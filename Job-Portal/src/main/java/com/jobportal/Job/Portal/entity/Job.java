package com.jobportal.Job.Portal.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.jobportal.Job.Portal.dto.Applicant;
import com.jobportal.Job.Portal.dto.JobStatus;
import org.springframework.data.annotation.Id;

public class Job {

    @Id
    private Long id;

    private String jobTitle;

    private String company;

    private List<Applicant> applicants;

    private String about;

    private String experience;

    private String jobType;
    private String location;

    private Long packageOffered;
    private LocalDateTime postTime;

    private String description;

    private List<String> skillsRequired;

    private JobStatus jobStatus;

}
