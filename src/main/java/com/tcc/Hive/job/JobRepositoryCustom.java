package com.tcc.Hive.job;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface JobRepositoryCustom {

    Page<Job> findJobsByKeywords(String[] keywords, Pageable pageable);


}
