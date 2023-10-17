package com.tcc.Hive.job;

import com.tcc.Hive.company.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;

public interface JobRepository extends MongoRepository<Job, Integer>, JobRepositoryCustom {

     Job findByJobTitle(String name);
     Job findById(String id);

     ArrayList<Job> findByCompanyIdIgnoreCase(String company);

    Page<Job> findByCompanyIdIgnoreCase(String company, Pageable pageable);

    Page<Job> findByCompanyIdAndActiveFalse(String companyId, Pageable pageable);
    }
