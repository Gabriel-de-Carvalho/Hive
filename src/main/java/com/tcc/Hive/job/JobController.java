package com.tcc.Hive.job;

import com.tcc.Hive.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    public void joinRecruitmentProcess(@PathVariable("id") int id, @RequestBody User user){
        jobService.JoinSelectionJob(id, user);
    }

    @GetMapping("/{id}/")
    public ArrayList<User> getAllParticipants(@PathVariable("id") int id){
        return jobService.participantsFromJob(id);
    }
}
