package com.tcc.Hive.user;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public User createUsuario(User user) {
        try {
            User currentUser = findUser(user.getUser());
            if(currentUser == null){
                return userRepository.save(user);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario ja existe") ;

    }

    public User findUser(String name){
        try{
            User user = userRepository.findByUser(name);
            if(user != null){
                return user;
            }

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não está cadastrado");
    }

//    public User updateUser(User user){
//
//    }
}
