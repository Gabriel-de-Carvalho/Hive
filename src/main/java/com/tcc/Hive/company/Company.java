package com.tcc.Hive.company;

import com.tcc.Hive.job.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Document("company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {


    @NotNull
    @NotEmpty
    @Id
    private String companyName;
    private String typeOfCompany;
    private String siteCompany;
    private String numberEmployees;
    private ArrayList<Job> jobsOpportunities;
    private String city;
    private String state;
    private String country;
}
