package com.example.ingressjobs.repository;

import com.example.ingressjobs.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobRepository extends JpaRepository<Job,Long>, JpaSpecificationExecutor<Job> {

}
