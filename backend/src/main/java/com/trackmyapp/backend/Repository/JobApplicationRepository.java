package com.trackmyapp.backend.Repository;

import com.trackmyapp.backend.Entity.JobApplication;
import com.trackmyapp.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {

    List<JobApplication> findByUserId(Long userId);
    List<JobApplication> findByUser(User user);


    Optional<JobApplication> findByIdAndUser(Long jobId, User user);
}
