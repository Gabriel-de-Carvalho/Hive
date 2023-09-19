package com.tcc.Hive.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/companyName")
    public ArrayList<Company> findCompanies(@PathVariable String companyName){
        return companyService.findCompany(companyName);
    }

    @PostMapping("/")
    public Company createCompany(@RequestBody Company newCompany){
        return companyService.createCompanyPage(newCompany);
    }

    @GetMapping("/")
    public Company loggedCompany(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return companyService.findByEmail(auth.getName());
    }
}
