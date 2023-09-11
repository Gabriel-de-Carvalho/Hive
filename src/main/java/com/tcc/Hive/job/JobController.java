package com.tcc.Hive.job;

import com.tcc.Hive.user.UserHive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/")
    public Job getJob(@RequestParam String jobTitle){
        return jobService.FindJobByTitle(jobTitle);
    }

    @PostMapping("/{id}/participant/")
    public void joinRecruitmentProcess(@PathVariable("id") String id){
        jobService.JoinSelectionJob(id);
    }

    @GetMapping("/{id}/")
    public List<UserHive> getAllParticipants(@PathVariable("id") String id){
        return jobService.participantsFromJob(id);
    }

    @PostMapping("/")
    public Job createJobOpportunitie(@RequestBody Job job){
        return jobService.newJobOportunitie(job);
    }

    @GetMapping("/search/")
    public List<Job> getJobsByKeywords(@RequestParam String keywords){
        return jobService.getJobsByKeywords(keywords.split(","));
    }

    @GetMapping("/admin")
    public ResponseEntity<String> testeRotaAuthenticated(){
        return ResponseEntity.ok("teste");
    }
}
