package com.example.ingressjobs.service;

import com.example.ingressjobs.dto.request.JobRequest;
import com.example.ingressjobs.dto.response.JobResponse;
import com.example.ingressjobs.enums.ExperienceLevel;
import com.example.ingressjobs.enums.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface JobService {

    void addJob(JobRequest jobRequest);

    void updateJobByID(JobRequest jobRequest, Long id);

    JobResponse getJobByID(Long id);

    Page<JobResponse> getAllJobs(Pageable pageable);

    void deleteJobByID(Long id);


    Page<JobResponse> findBySpecification(String jobTittle, String companyName, List<String> location,
                                          JobType jobType,
                                          ExperienceLevel experienceLevel,
                                          LocalDate postedDate, String tags, Pageable pageable);


    Page<JobResponse> sortJobsBySalaryRangeAscending(Pageable pageable);

    Page<JobResponse> sortJobsBySalaryRangeDescending(Pageable pageable);

    Page<JobResponse> sortJobsByPostedDateAscending(Pageable pageable);

    Page<JobResponse> sortJobsByPostedDateDescending(Pageable pageable);

}
