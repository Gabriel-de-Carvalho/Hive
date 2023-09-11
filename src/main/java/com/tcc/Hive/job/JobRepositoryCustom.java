package com.tcc.Hive.job;

import java.util.ArrayList;
import java.util.List;

public interface JobRepositoryCustom {

    List<Job> findJobsByKeywords(String[] keywords);
}
