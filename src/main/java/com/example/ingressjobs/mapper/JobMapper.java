package com.example.ingressjobs.mapper;

import com.example.ingressjobs.dto.request.JobRequest;
import com.example.ingressjobs.dto.response.JobResponse;
import com.example.ingressjobs.model.Job;
import org.mapstruct.*;

import java.time.LocalDate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper {


    Job toJobEntity(JobRequest jobRequest);

    @AfterMapping
    default void setAddTime(@MappingTarget Job job) {
        job.setPostedDate(LocalDate.now());
    }

    JobResponse toJobResponse(Job job);
}
