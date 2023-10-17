package com.tcc.Hive.job;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobRepositoryCustomImpl implements JobRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    public JobRepositoryCustomImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<Job> findJobsByKeywords(String[] keywords, Pageable pageable) {
        Query query = new Query();
        for(String keyword: keywords){
            Criteria criteria = new Criteria();
            criteria.orOperator(Criteria.where("jobDesc").regex(keyword, "i"), Criteria.where("jobTitle").regex(keyword, "i")).andOperator(Criteria.where("active").is(true));
            query.addCriteria(criteria);
        }
        query.with(pageable);
        List<Job> jobs = mongoTemplate.find(query, Job.class);
        return PageableExecutionUtils.getPage(
                jobs,
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Job.class)
        );

    }

//    @Override
//    public List<Job> findByCompanyIdAAndActiveFalse(String companyId, Pageable pageable) {
//        Query query = new Query();
//
//        Criteria criteria = new Criteria();
//        criteria.andOperator(Criteria.where("companyId").is(companyId), Criteria.where("active").is(false));
//        query.addCriteria(criteria);
//        query.with(pageable);
//        List<Jobs>
//
//        return mongoTemplate.find(query, Job.class);
//    }
}
