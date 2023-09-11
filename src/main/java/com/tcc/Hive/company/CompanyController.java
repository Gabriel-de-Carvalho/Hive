package com.tcc.Hive.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "*")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/")
    public ArrayList<Company> findCompanies(@RequestParam String companyName){
        return companyService.findCompany(companyName);
    }

    @PostMapping("/")
    public Company createCompany(@RequestBody Company newCompany){
        return companyService.createCompanyPage(newCompany);
    }
}
