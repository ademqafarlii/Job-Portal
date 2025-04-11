package com.example.ingressjobs.model;

import com.example.ingressjobs.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTittle;
    private String companyName;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Enumerated(EnumType.STRING)
    private JobType jobType;


    private Integer minimumSalary;
    private Integer maximumSalary;
    private String jobDescription;

    private String skills;
    private String qualifications;
    private String experience;
    private String certifications;

    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    @Enumerated(EnumType.STRING)
    private Industry industry;
    private LocalDate postedDate;
    private LocalDate applicationDate;
    private String howToApply;
    private String companyLogo;
    private String benefits;
    private String tags;
    private String source;

}
