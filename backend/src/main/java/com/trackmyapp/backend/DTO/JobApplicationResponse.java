package com.trackmyapp.backend.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobApplicationResponse {

    private Long id;
    private String companyName;
    private String jobTitle;
    private String jobLocation;
    private String jobType;
    private String applicationLink;
    private LocalDateTime appliedDate;
    private String status;
    private String skills;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

