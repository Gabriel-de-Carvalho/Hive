package com.tcc.Hive.company;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface CompanyRepository extends MongoRepository<Company, String> {

    Company getCompanyByCompanyName(String companyName);

    ArrayList<Company> getCompaniesByCompanyNameContainingIgnoreCase(String companyName);

    Company getCompanyByCompanyEmail(String companyEmail);

}
