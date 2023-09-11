package com.tcc.Hive.security;

import com.tcc.Hive.dto.UserAuthenticationDTO;
import com.tcc.Hive.security.utils.JWTUtils;
import com.tcc.Hive.user.UserHive;
import com.tcc.Hive.user.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> generateToken(@RequestBody UserAuthenticationDTO user){
        if(user == null || user.getUserName().isEmpty() || user.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "campos de usuário e senha não podem ser nulos");
        }

        UserHive userHive = userService.findUser(user.getUserName());
        if(!userHive.getPassword().equals(user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha incorreta");
        }

        return ResponseEntity.ok(jwtUtils.generateAcessToken(userHive));
    }

    @GetMapping("/teste")
    public Claims testeRota(@RequestParam("token") String token){
        return jwtUtils.getClaims(token);
    }

}
