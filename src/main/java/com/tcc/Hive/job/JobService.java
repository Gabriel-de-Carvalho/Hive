package com.tcc.Hive.job;

import com.tcc.Hive.company.Company;
import com.tcc.Hive.company.CompanyRepository;
import com.tcc.Hive.user.UserHive;
import com.tcc.Hive.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    public Job FindJobByTitle(String title){
        try{
            return jobRepository.findByJobTitle(title);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void JoinSelectionJob(String id){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserHive userHive = userRepository.findByEmail(authentication.getName());
            UserHive userHiveCurrent = userRepository.findByEmail(userHive.getEmail());

            Job job = jobRepository.findById(id);
            if(job.getParticipants().contains(userHive.getEmail())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuário já cadastrado na vaga");
            }
            job.joinParticipant(userHiveCurrent);
            jobRepository.save(job);

    }

    public List<UserHive> participantsFromJob(String id){
        try{

            Job job =  jobRepository.findById(id);
            List<UserHive> users = userRepository.findAllByEmailIn(job.getParticipants());
            return users;
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public Job newJobOportunitie(Job job){
        Job newJob = null;
        try{
            newJob = jobRepository.save(job);

            Company company = companyRepository.getCompanyByCompanyName(newJob.getCompanyId());
            if(company.getJobsOpportunitiesIds() == null){
                company.setJobsOpportunitiesIds(new ArrayList<>());
            }
            company.getJobsOpportunitiesIds().add(newJob.getId());
            companyRepository.save(company);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return newJob;
    }

    public List<Job> getJobsByKeywords(String[] keywords){
        List<Job> jobs = jobRepository.findJobsByKeywords(keywords);
        if(jobs.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "não há empregos de acordo com as palavras de busca");
        }
        return jobs;
    }


}
