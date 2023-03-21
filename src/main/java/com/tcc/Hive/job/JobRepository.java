package com.tcc.Hive.job;

import com.tcc.Hive.company.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface JobRepository extends MongoRepository<Job, Integer> {

    public Job findByJobTitle(String name);
    public Job findById(int id);

    public ArrayList<Job> findByCompany(Company company);
    }
