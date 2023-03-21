package com.tcc.Hive.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User createNewUser(@RequestBody User user){
        return userService.createUsuario(user);
    }

    @GetMapping("/")
    public User getUsuario(@RequestParam String name){
        return userService.findUser(name);
    }
}
