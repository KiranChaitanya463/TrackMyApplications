package com.trackmyapp.backend.Controller;

import com.trackmyapp.backend.DTO.JobApplicationRequest;
import com.trackmyapp.backend.DTO.JobApplicationResponse;
import com.trackmyapp.backend.Entity.JobApplication;
import com.trackmyapp.backend.Entity.User;
import com.trackmyapp.backend.Service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<JobApplicationResponse> createJob(@RequestBody JobApplicationRequest request){
        JobApplicationResponse response= jobApplicationService.createJob(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationResponse>> getAllJobs(){
        List<JobApplicationResponse> jobs=jobApplicationService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> getJobById(@PathVariable Long id){
        JobApplicationResponse job=jobApplicationService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponse> updateJob(@PathVariable Long id,@RequestBody JobApplicationRequest request){
        JobApplicationResponse response=jobApplicationService.updateJob(id,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteJob(@PathVariable Long id){
        Long response=jobApplicationService.deleteJob(id);
        return ResponseEntity.ok(response);
    }
}
