package com.trackmyapp.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationRequest {

    private String companyName;
    private String jobTitle;
    private String jobLocation;
    private String jobType;
    private String applicationLink;
    private LocalDateTime appliedDate;
    private String status;
    private String skills;
    private String notes;

}
