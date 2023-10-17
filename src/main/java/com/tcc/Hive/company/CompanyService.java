package com.tcc.Hive.company;

import com.tcc.Hive.user.UserHive;
import com.tcc.Hive.user.UserRepository;
import com.tcc.Hive.user.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Company updateCompany(Company companyNewInfo){
        Company currentCompany = companyRepository.getCompanyByCompanyEmail(companyNewInfo.getCompanyEmail());
        if(currentCompany == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empresa não cadastrada");
        }

        currentCompany.setCompanyName(companyNewInfo.getCompanyName());
        currentCompany.setSiteCompany(companyNewInfo.getSiteCompany());
        currentCompany.setCity(companyNewInfo.getCity());
        currentCompany.setState(companyNewInfo.getState());
        currentCompany.setCountry(companyNewInfo.getCountry());
        currentCompany.setNumberEmployees(companyNewInfo.getNumberEmployees());

        companyRepository.save(currentCompany);

        return currentCompany;
    }

    public ResponseEntity deleteCompany(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String companyEmail = auth.getName();
        Company company = companyRepository.getCompanyByCompanyEmail(companyEmail);

        if(company == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa já foi deletada");
        }

        companyRepository.delete(company);
        return new ResponseEntity(HttpStatus.OK);
    }
}
