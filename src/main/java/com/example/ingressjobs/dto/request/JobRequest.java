package com.example.ingressjobs.dto.request;

import com.example.ingressjobs.enums.*;
import com.example.ingressjobs.model.Location;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JobRequest {

    @NotBlank
    private String jobTittle;
    @NotBlank
    private String companyName;

    @NotNull
    private Location location;

    private JobType jobType;


    private Integer minimumSalary;

    private Integer maximumSalary;
    @NotBlank
    private String jobDescription;

    private String skills;
    private String qualifications;
    private String experience;
    private String certifications;

    private ExperienceLevel experienceLevel;

    private EducationLevel educationLevel;

    private Industry industry;
    private LocalDate postedDate;
    private LocalDate applicationDate;
    private String howToApply;
    private String companyLogo;
    private String benefits;
    private String tags;
    private String source;
}
