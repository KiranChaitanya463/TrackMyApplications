package com.trackmyapp.backend.DTO;

import java.time.LocalDateTime;

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

    // Optional: if you're not using session/JWT-based auth, use this to associate the user
    private Long userId;

    public JobApplicationRequest() {}

    public JobApplicationRequest(String companyName, String jobTitle, String jobLocation, String jobType,
                                 String applicationLink, LocalDateTime appliedDate, String status,
                                 String skills, String notes, Long userId) {
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.jobLocation = jobLocation;
        this.jobType = jobType;
        this.applicationLink = applicationLink;
        this.appliedDate = appliedDate;
        this.status = status;
        this.skills = skills;
        this.notes = notes;
        this.userId = userId;
    }

    // Getters and Setters
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getJobLocation() { return jobLocation; }
    public void setJobLocation(String jobLocation) { this.jobLocation = jobLocation; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public String getApplicationLink() { return applicationLink; }
    public void setApplicationLink(String applicationLink) { this.applicationLink = applicationLink; }

    public LocalDateTime getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDateTime appliedDate) { this.appliedDate = appliedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
