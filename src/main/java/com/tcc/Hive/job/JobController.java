package com.tcc.Hive.job;

import com.tcc.Hive.dto.JobOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping(value = "/{id}/participant/", consumes = {"multipart/form-data"})
    public void joinRecruitmentProcess(@PathVariable("id") String id, @RequestBody MultipartFile file) throws IOException {
        ParticipantApplication participantApplication = new ParticipantApplication();
        jobService.JoinSelectionJob(id, file);
    }

    @GetMapping("/{id}/participants")
    public JobOutputDto getAllParticipants(@PathVariable("id") String id){
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

    @GetMapping("/download")
    public byte[] download(){
        return jobService.getParticipant();
    }

    @GetMapping("/company")
    public List<Job> getAllJobsFromCompany(){
        return jobService.getJobsFromCompany();
    }

    @GetMapping("{jobId}/applicant/{email}/curriculum/")
    public byte[] getCurriculum(@PathVariable("jobId") String jobId, @PathVariable("email") String email){
        return jobService.getParticipantCurriculum(jobId, email);
    }

    @PutMapping("{jobId}/close")
    public boolean getCurriculum(@PathVariable("jobId") String jobId){
        return jobService.closeJobOpportunity(jobId);
    }
}
