package com.tcc.Hive.user;

import com.tcc.Hive.experience.Experience;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public UserHive createNewUser(@RequestBody UserHive userHive){
        return userService.createUsuario(userHive);
    }

    @GetMapping("/")
    public UserHive getUsuario(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return userService.findUser(user.getUsername());
    }

    @GetMapping("/hello/")
    public String helloWorld(){
        return "hello";
    }

    @PutMapping("/experience/")
    public List<Experience> newExperience(@RequestBody Experience experience){
        if(experience == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "experiencia não pode ser vazia");
        }
        return userService.addExperience(experience);
    }

    @PutMapping("/")
    public ResponseEntity updateUser(@RequestBody UserHive userHive){
        return new ResponseEntity(userService.updateUser(userHive), HttpStatus.OK);
    }
}
