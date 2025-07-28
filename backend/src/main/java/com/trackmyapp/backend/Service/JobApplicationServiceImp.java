package com.trackmyapp.backend.Service;

import com.trackmyapp.backend.DTO.JobApplicationRequest;
import com.trackmyapp.backend.DTO.JobApplicationResponse;
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


    private User getCurrentUser(){
        var auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth==null || !(auth.getPrincipal() instanceof User)){
            throw new IllegalStateException("No authenticated user found");
        }
        return (User) auth.getPrincipal();
    }

    @Override
    public JobApplicationResponse createJob(JobApplicationRequest request) {
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
        JobApplication savedJob=jobApplicationRepository.save(job);
        return mapToResponse(savedJob);
    }

    @Override
    public List<JobApplicationResponse> getAllJobs() {
        User user=getCurrentUser();
        return jobApplicationRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public JobApplicationResponse getJobById(Long jobId) {
        User user=getCurrentUser();
        JobApplication response= jobApplicationRepository.findByIdAndUser(jobId,user)
                .orElseThrow(()-> new RuntimeException("Job not found or not authorised"));
        return mapToResponse(response);
    }

    @Override
    public JobApplicationResponse updateJob(Long jobId, JobApplicationRequest request) {
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

        JobApplication updatedJob=jobApplicationRepository.save(existing);
        return mapToResponse(updatedJob);
    }

    @Override
    public Long deleteJob(Long jobId) {
        User user = getCurrentUser();

        JobApplication job = jobApplicationRepository.findByIdAndUser(jobId, user)
                .orElseThrow(() -> new RuntimeException("Job not found or not authorized"));

        jobApplicationRepository.delete(job);
        return jobId;
    }

    private JobApplicationResponse mapToResponse(JobApplication job) {
        return new JobApplicationResponse(
                job.getId(),
                job.getCompanyName(),
                job.getJobTitle(),
                job.getJobLocation(),
                job.getJobType(),
                job.getApplicationLink(),
                job.getAppliedDate(),
                job.getStatus(),
                job.getSkills(),
                job.getNotes(),
                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }
}
