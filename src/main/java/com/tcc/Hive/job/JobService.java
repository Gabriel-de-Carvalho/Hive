package com.tcc.Hive.job;

import com.tcc.Hive.company.Company;
import com.tcc.Hive.company.CompanyRepository;
import com.tcc.Hive.dto.JobOutputDto;
import com.tcc.Hive.dto.ParticipantOutputDto;
import com.tcc.Hive.user.UserHive;
import com.tcc.Hive.user.UserRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void JoinSelectionJob(String id, MultipartFile file) throws IOException {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserHive userHive = userRepository.findByEmail(authentication.getName());

            ParticipantApplication application = new ParticipantApplication();
            application.setIdApplicant(userHive.getEmail());
            application.setPdfUser(new Binary(BsonBinarySubType.BINARY, file.getBytes()));


            Job job = jobRepository.findById(id);
            if(job.getParticipants() == null){
                job.setParticipants(new ArrayList<>());
            }

            if(job.getParticipants().contains(application)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuário já cadastrado na vaga");
            }

            if(userHive.getJobOpportunitiesIds() == null){
                userHive.setJobOpportunitiesIds(new ArrayList<>());
            }

            if(userHive.getJobOpportunitiesIds().contains(job.getId())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuário já está cadastrado na vaga");
            }

            userHive.getJobOpportunitiesIds().add(job.getId());
            userRepository.save(userHive);
            job.joinParticipant(application);
            jobRepository.save(job);

    }

    public byte[] getParticipant(){
        Job job = jobRepository.findByCompanyIdIgnoreCase("magalu").get(0);

        ParticipantApplication participantApplication = job.getParticipants().get(0);
        return participantApplication.getPdfUser().getData();
    }

    public JobOutputDto participantsFromJob(String id){
        try{

            Job job =  jobRepository.findById(id);
            List<ParticipantApplication> users = job.getParticipants();
            JobOutputDto participant = new JobOutputDto();
            participant.setParticipants(new ArrayList<>());

            for(ParticipantApplication application: users){
                UserHive user = userRepository.findByEmail(application.getIdApplicant());
                participant.getParticipants().add(new ParticipantOutputDto(user.getUser(), user.getEmail()));
            }
            return participant;
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public Job newJobOportunitie(Job job){
        Job newJob = null;
        try{

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String companyEmail = auth.getName();
            Company company = companyRepository.getCompanyByCompanyEmail(companyEmail);
            newJob = job;
            newJob.setCompanyId(company.getCompanyEmail());
            newJob.setActive(true);
            newJob = jobRepository.save(newJob);

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

    public ResponseEntity<Map<String, Object>> getJobsByKeywords(String[] keywords, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> jobs = jobRepository.findJobsByKeywords(keywords, pageable);
        if(jobs.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "não há empregos de acordo com as palavras de busca");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("jobs", jobs);
        response.put("currentPage", jobs.getNumber());
        response.put("totalItems", jobs.getTotalElements());
        response.put("totalPages", jobs.getTotalPages());


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getJobsFromCompany(int pageSize, int size){
        Pageable page = PageRequest.of(pageSize, size);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String companyId = auth.getName();
        Page<Job> jobs = jobRepository.findByCompanyIdIgnoreCase(companyId, page);

        Map<String, Object> response = new HashMap<>();
        response.put("jobs", jobs);
        response.put("currentPage", jobs.getNumber());
        response.put("totalItems", jobs.getTotalElements());
        response.put("totalPages", jobs.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public byte[] getParticipantCurriculum(String jobId, String email){
        Job job = jobRepository.findById(jobId);
        ParticipantApplication participantApplication = null;
        for(ParticipantApplication participant: job.getParticipants()){
            if(participant.getIdApplicant().equals(email)){
                participantApplication = participant;
                break;
            }
        }
        if(participantApplication != null){
            return participantApplication.getPdfUser().getData();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não cadastrado no processo seletivo");
        }
    }

    public boolean closeJobOpportunity(String jobId){
        Job job = jobRepository.findById(jobId);
        job.setActive(false);
        jobRepository.save(job);
        return true;
    }

    public ResponseEntity<Map<String, Object>> getClosedJobs(int pageCount, int size){
        Pageable page = PageRequest.of(pageCount, size);


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String companyEmail = auth.getName();
        Company company = companyRepository.getCompanyByCompanyEmail(companyEmail);

        Page<Job> closedJobs = jobRepository.findByCompanyIdAndActiveFalse(company.getCompanyEmail(), page);
        if(closedJobs == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "não há trabalhos cadastrados");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("jobs", closedJobs);
        response.put("currentPage", closedJobs.getNumber());
        response.put("totalItems", closedJobs.getTotalElements());
        response.put("totalPages", closedJobs.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
