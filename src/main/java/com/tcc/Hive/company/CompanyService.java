package com.tcc.Hive.company;

import com.tcc.Hive.user.UserHive;
import com.tcc.Hive.user.UserRepository;
import com.tcc.Hive.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;

    public Company createCompanyPage(Company newCompany){
            Company company = companyRepository.getCompanyByCompanyEmail(newCompany.getCompanyEmail());
            UserHive user = userRepository.findByEmail(newCompany.getCompanyEmail());
            if(company == null && user == null){
                if(newCompany.getJobsOpportunitiesIds() == null){
                    newCompany.setJobsOpportunitiesIds(new ArrayList<>());
                }
                return companyRepository.save(newCompany);
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

    public Company findByEmail(String companyEmail){
        return companyRepository.getCompanyByCompanyEmail(companyEmail);
    }
}
