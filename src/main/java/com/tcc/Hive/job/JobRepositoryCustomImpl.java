package com.tcc.Hive.job;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobRepositoryCustomImpl implements JobRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    public JobRepositoryCustomImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Job> findJobsByKeywords(String[] keywords) {
        Query query = new Query();
        for(String keyword: keywords){
            Criteria criteria = new Criteria();
            criteria.orOperator(Criteria.where("jobDesc").regex(keyword, "i"), Criteria.where("jobTitle").regex(keyword, "i")).andOperator(Criteria.where("active").is(true));
            query.addCriteria(criteria);
        }

        return mongoTemplate.find(query, Job.class);

    }
}
