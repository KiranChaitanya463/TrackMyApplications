package com.trackmyapp.backend.Service;

import com.trackmyapp.backend.DTO.JobApplicationRequest;
import com.trackmyapp.backend.DTO.JobApplicationResponse;
import com.trackmyapp.backend.Entity.JobApplication;

import java.util.List;

public interface JobApplicationService {

    JobApplicationResponse createJob(JobApplicationRequest request);
    List<JobApplicationResponse> getAllJobs();
    JobApplicationResponse getJobById(Long jobId);
    JobApplicationResponse updateJob(Long jobId, JobApplicationRequest request);
    Long deleteJob(Long jobId);
}
