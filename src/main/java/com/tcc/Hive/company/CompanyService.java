package com.tcc.Hive.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompanyPage(Company newCompany){
        try{
            Company company = companyRepository.getCompanyByCompanyName(newCompany.getCompanyName());
            if(company == null){
                return companyRepository.save(newCompany);
            }
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "empresa ja existe");
    }

    public ArrayList<Company> findCompany(String companyName){
            try{
                return companyRepository.getCompaniesByCompanyNameContainingIgnoreCase(companyName);
            }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
    }
}
