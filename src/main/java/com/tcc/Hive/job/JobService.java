package com.tcc.Hive.job;

import com.tcc.Hive.user.User;
import com.tcc.Hive.user.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    public Job FindJobByTitle(String title){
        try{
            return jobRepository.findByJobTitle(title);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void JoinSelectionJob(int id,  User user){

        try{
            Job job = jobRepository.findById(id);
            User userCurrent = userRepository.findByEmail(user.getEmail());

            job.joinParticipant(userCurrent);

            jobRepository.save(job);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    public ArrayList<User> participantsFromJob(int id){
        try{
            return jobRepository.findById(id).getParticipants();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

//    public ArrayList<Job> jobsFromCompany(String company){
//        try{
//
//        }
//    }
}
