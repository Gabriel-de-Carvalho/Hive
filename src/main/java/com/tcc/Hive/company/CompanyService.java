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
        if(companyNewInfo.getCompanyName() != null && !companyNewInfo.getCompanyName().isEmpty()){
            currentCompany.setCompanyName(companyNewInfo.getCompanyName());
        }
        if(companyNewInfo.getSiteCompany() != null && !companyNewInfo.getSiteCompany().isEmpty()){
            currentCompany.setSiteCompany(companyNewInfo.getSiteCompany());
        }
        if(companyNewInfo.getCity() != null && !companyNewInfo.getCity().isEmpty()){
            currentCompany.setCity(companyNewInfo.getCity());
        }
        if(companyNewInfo.getState() != null && !companyNewInfo.getState().isEmpty()){
            currentCompany.setState(companyNewInfo.getState());
        }
        if(companyNewInfo.getCountry() != null && !companyNewInfo.getCountry().isEmpty()){
            currentCompany.setCountry(companyNewInfo.getCountry());
        }
        if(companyNewInfo.getNumberEmployees() != null && companyNewInfo.getNumberEmployees() > 0){
            currentCompany.setNumberEmployees(companyNewInfo.getNumberEmployees());
        }

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
