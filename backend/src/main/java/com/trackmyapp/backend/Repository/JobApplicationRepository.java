package com.trackmyapp.backend.Repository;

import com.trackmyapp.backend.Entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {

    List<JobApplication> findByUserId(Long userId);
}
