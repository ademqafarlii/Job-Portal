package com.example.ingressjobs.service.impl;

import com.example.ingressjobs.dto.request.JobRequest;
import com.example.ingressjobs.dto.response.JobResponse;
import com.example.ingressjobs.enums.ExperienceLevel;
import com.example.ingressjobs.enums.JobType;
import com.example.ingressjobs.exception.JobNotFoundException;
import com.example.ingressjobs.mapper.JobMapper;
import com.example.ingressjobs.model.Job;
import com.example.ingressjobs.repository.JobRepository;
import com.example.ingressjobs.service.JobService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobMapper jobMapper;
    private final JobRepository jobRepository;

    @Override
    public void addJob(JobRequest jobRequest) {
        jobRepository.save(jobMapper.toJobEntity(jobRequest));
    }

    @Override
    public void updateJobByID(JobRequest jobRequest, Long id) {
        Optional<Job> existingJob = jobRepository.findById(id);
        if (existingJob.isEmpty()) {
            throw new JobNotFoundException("Job not found");
        }
        existingJob.get().setJobTittle(jobRequest.getJobTittle());
        existingJob.get().setCompanyName(jobRequest.getCompanyName());
        existingJob.get().setLocation(jobRequest.getLocation());
        existingJob.get().setJobType(jobRequest.getJobType());
        existingJob.get().setMinimumSalary(jobRequest.getMinimumSalary());
        existingJob.get().setMaximumSalary(jobRequest.getMaximumSalary());
        existingJob.get().setExperience(jobRequest.getExperience());
        existingJob.get().setQualifications(jobRequest.getQualifications());
        existingJob.get().setSkills(jobRequest.getSkills());
        existingJob.get().setCertifications(jobRequest.getCertifications());
        existingJob.get().setJobDescription(jobRequest.getJobDescription());
        existingJob.get().setExperienceLevel(jobRequest.getExperienceLevel());
        existingJob.get().setEducationLevel(jobRequest.getEducationLevel());
        existingJob.get().setIndustry(jobRequest.getIndustry());
        existingJob.get().setPostedDate(jobRequest.getPostedDate());
        existingJob.get().setApplicationDate(jobRequest.getApplicationDate());
        existingJob.get().setHowToApply(jobRequest.getHowToApply());
        existingJob.get().setCompanyLogo(jobRequest.getCompanyLogo());
        existingJob.get().setBenefits(jobRequest.getBenefits());
        existingJob.get().setTags(jobRequest.getTags());
        existingJob.get().setSource(jobRequest.getSource());

        jobRepository.save(existingJob.get());

    }

    @Override
    public JobResponse getJobByID(Long id) {
        return jobRepository.findById(id)
                .stream()
                .map(jobMapper::toJobResponse)
                .findFirst()
                .orElseThrow(() -> new JobNotFoundException("Job not found"));
    }

    @Override
    public Page<JobResponse> getAllJobs(Pageable pageable) {
        Page<Job> jobPage = jobRepository.findAll(pageable);
        if (jobPage.getContent().isEmpty()) {
            throw new JobNotFoundException("Job not found");
        }
        return jobPage.map(jobMapper::toJobResponse);
    }

    @Override
    public void deleteJobByID(Long id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isEmpty()) {
            throw new JobNotFoundException("Job not found");
        }
        jobRepository.deleteById(id);
    }

    @Override
    public Page<JobResponse> sortJobsBySalaryRangeAscending(Pageable pageable) {
        return jobRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("minimumSalary").ascending())).map(jobMapper::toJobResponse);
    }

    @Override
    public Page<JobResponse> sortJobsBySalaryRangeDescending(Pageable pageable) {
        return jobRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("maximumSalary").descending())).map(jobMapper::toJobResponse);
    }


    @Override
    public Page<JobResponse> sortJobsByPostedDateAscending(Pageable pageable) {
        return jobRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("postedDate").ascending())).map(jobMapper::toJobResponse);
    }

    @Override
    public Page<JobResponse> sortJobsByPostedDateDescending(Pageable pageable) {
        return jobRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("postedDate").descending())).map(jobMapper::toJobResponse);
    }

    @Override
    public Page<JobResponse> findBySpecification(String jobTittle, String companyName, List<String> location,
                                                 JobType jobType, ExperienceLevel experienceLevel,
                                                 LocalDate postedDate, String tags,
                                                 Pageable pageable) {
        Specification<Job> specification;

        specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (jobTittle != null) {
                predicateList.add(criteriaBuilder.equal(root.get("jobTittle"), jobTittle));
            }
            if (companyName != null) {
                predicateList.add(criteriaBuilder.equal(root.get("companyName"), companyName));
            }
            if (location != null) {
                predicateList.add(criteriaBuilder.equal(root.get("location"), location));
            }
            if (jobType != null) {
                predicateList.add(criteriaBuilder.equal(root.get("jobType"), jobType));
            }

            if (experienceLevel != null) {
                predicateList.add(criteriaBuilder.equal(root.get("experienceLevel"), experienceLevel));
            }

            if (postedDate != null) {
                predicateList.add(criteriaBuilder.equal(root.get("postedDate"), postedDate));
            }

            if (tags != null) {
                predicateList.add(criteriaBuilder.equal(root.get("tags"), tags));
            }

            Objects.requireNonNull(query).where(
                    criteriaBuilder.and(predicateList.toArray(predicateList.toArray(new Predicate[0])))
            );

            return query.getRestriction();
        };
        return jobRepository.findAll(specification, pageable).map(jobMapper::toJobResponse);
    }


}
