package com.tcc.Hive.user;

import com.tcc.Hive.company.Company;
import com.tcc.Hive.company.CompanyService;
import com.tcc.Hive.experience.Experience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyService companyService;

    public UserHive createUsuario(UserHive userHive) {
        try {
            UserHive currentUserHive = userRepository.findByEmail(userHive.getEmail());
            Company company = companyService.findByEmail(userHive.getEmail());
            if(currentUserHive == null && company == null){
                return userRepository.save(userHive);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario ja existe") ;

    }

    public UserHive findUser(String name){
        try{
            UserHive userHive = userRepository.findByEmail(name);
            if(userHive != null){
                return userHive;
            }

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não está cadastrado");
    }

    public List<Experience> addExperience(Experience experience){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserHive userHive = userRepository.findByEmail(authentication.getName());
        if(userHive.getExperiences() == null){
            userHive.setExperiences(new ArrayList<>());
        }
        experience.setId(userHive.getExperiences().size() + 1);
        userHive.getExperiences().add(experience);

        userRepository.save(userHive);
        return userHive.getExperiences();
    }

    public UserHive updateUser(UserHive userInfoUpdated){
        UserHive currentUser = userRepository.findByEmail(userInfoUpdated.getEmail());

        if(currentUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não cadastrado");
        }

        currentUser.setUser(userInfoUpdated.getUser());
        currentUser.setCurrentJob(userInfoUpdated.getCurrentJob());
        currentUser.setBio(userInfoUpdated.getBio());

        userRepository.save(currentUser);
        return currentUser;
    }
}
