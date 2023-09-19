package com.tcc.Hive.security.service;

import com.tcc.Hive.company.Company;
import com.tcc.Hive.company.CompanyRepository;
import com.tcc.Hive.user.UserHive;
import com.tcc.Hive.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserAuthenticateService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(!email.isEmpty()) {
            UserHive userHive = userRepository.findByEmail(email);
            if(userHive == null){
                Company company = companyRepository.getCompanyByCompanyEmail(email);
                UserDetails userDetails = new User(company.getCompanyEmail(), company.getPassword(), new ArrayList<>());
                return userDetails;
            } else {
                UserDetails userDetails = new User(userHive.getEmail(), userHive.getPassword(), new ArrayList<>());
                return userDetails;
            }
        } else {
            throw new RuntimeException("usuário invalido");
        }
    }

    public String checkTypeAccount(String email){
        UserHive user = userRepository.findByEmail(email);

        if(user != null){
            return "user";
        }

        Company company = companyRepository.getCompanyByCompanyEmail(email);
        if(company != null){
            return "company";
        }

        return "non-existent";
    }
}
