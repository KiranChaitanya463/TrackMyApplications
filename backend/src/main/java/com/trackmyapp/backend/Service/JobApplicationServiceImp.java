package com.trackmyapp.backend.Service;

import com.trackmyapp.backend.DTO.JobApplicationRequest;
import com.trackmyapp.backend.Entity.JobApplication;
import com.trackmyapp.backend.Entity.User;
import com.trackmyapp.backend.Repository.JobApplicationRepository;
import com.trackmyapp.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImp implements JobApplicationService{


    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;


    private User getCurrentUser(){
        var auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth==null || !(auth.getPrincipal() instanceof User)){
            throw new IllegalStateException("No authenticated user found");
        }
        return (User) auth.getPrincipal();
    }

    @Override
    public String createJob(JobApplicationRequest request) {
        User user=getCurrentUser();

        JobApplication job=JobApplication.builder()
                .companyName(request.getCompanyName())
                .jobTitle(request.getJobTitle())
                .jobLocation(request.getJobLocation())
                .jobType(request.getJobType())
                .applicationLink(request.getApplicationLink())
                .appliedDate(request.getAppliedDate())
                .status(request.getStatus())
                .notes(request.getNotes())
                .user(user)
                .build();
        jobApplicationRepository.save(job);
        return "Job application created successfully";
    }

    @Override
    public List<JobApplication> getAllJobs() {
        User user=getCurrentUser();
        return jobApplicationRepository.findByUser(user);
    }

    @Override
    public JobApplication getJobById(Long jobId) {
        User user=getCurrentUser();
        return jobApplicationRepository.findByIdAndUser(jobId,user)
                .orElseThrow(()-> new RuntimeException("Job not found or not authorised"));
    }

    @Override
    public String updateJob(Long jobId, JobApplicationRequest request) {
        User user=getCurrentUser();

        JobApplication existing = jobApplicationRepository.findByIdAndUser(jobId, user)
                .orElseThrow(() -> new RuntimeException("Job not found or not authorized"));

        existing.setCompanyName(request.getCompanyName());
        existing.setJobTitle(request.getJobTitle());
        existing.setJobLocation(request.getJobLocation());
        existing.setJobType(request.getJobType());
        existing.setApplicationLink(request.getApplicationLink());
        existing.setAppliedDate(request.getAppliedDate());
        existing.setStatus(request.getStatus());
        existing.setSkills(request.getSkills());
        existing.setNotes(request.getNotes());

        jobApplicationRepository.save(existing);
        return "Job application updated successfully.";
    }

    @Override
    public String deleteJob(Long jobId) {
        User user = getCurrentUser();

        JobApplication job = jobApplicationRepository.findByIdAndUser(jobId, user)
                .orElseThrow(() -> new RuntimeException("Job not found or not authorized"));

        jobApplicationRepository.delete(job);
        return "Job application deleted successfully.";
    }
}
