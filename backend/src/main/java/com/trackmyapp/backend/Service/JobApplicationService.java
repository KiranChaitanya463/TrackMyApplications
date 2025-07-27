package com.trackmyapp.backend.Service;

import com.trackmyapp.backend.DTO.JobApplicationRequest;
import com.trackmyapp.backend.Entity.JobApplication;

import java.util.List;

public interface JobApplicationService {

    String createJob(JobApplicationRequest request);
    List<JobApplication> getAllJobs();
    JobApplication getJobById(Long jobId);
    String updateJob(Long jobId, JobApplicationRequest request);
    String deleteJob(Long jobId);
}
