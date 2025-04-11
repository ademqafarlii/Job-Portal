package com.example.ingressjobs.repository;

import com.example.ingressjobs.model.Job;
import com.example.ingressjobs.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LocationRepository extends JpaRepository<Location,Long>, JpaSpecificationExecutor<Location> {
}
