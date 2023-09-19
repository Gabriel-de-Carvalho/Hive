package com.tcc.Hive.security;

import com.tcc.Hive.company.Company;
import com.tcc.Hive.company.CompanyService;
import com.tcc.Hive.dto.UserAuthenticationDTO;
import com.tcc.Hive.security.service.UserAuthenticateService;
import com.tcc.Hive.security.utils.CompanyAuthenticationDto;
import com.tcc.Hive.security.utils.JWTUtils;
import com.tcc.Hive.user.UserHive;
import com.tcc.Hive.user.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserAuthenticateService userAuthenticateService;

    @PostMapping("/signin/user")
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

    @PostMapping("/signin/company")
    public ResponseEntity<String> generateToken(@RequestBody CompanyAuthenticationDto company){
        if(company == null || company.getCompanyEmail().isEmpty() || company.getPassword().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "campos de email e senha não podem ser nulos");
        }

        Company companyHive = companyService.findByEmail(company.getCompanyEmail());
        if(!companyHive.getPassword().equals(company.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha incorreta");
        }

        return ResponseEntity.ok(jwtUtils.generateAcessToken(companyHive));
    }

    @GetMapping("/check/")
    public ResponseEntity<String> getTypeOfAccount(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(userAuthenticateService.checkTypeAccount(auth.getName()));
    }

}
