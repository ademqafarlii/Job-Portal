package com.example.ingressjobs.controller;

import com.example.ingressjobs.aop.customAnnotation.ConsoleLog;
import com.example.ingressjobs.dto.request.JobRequest;
import com.example.ingressjobs.dto.response.JobResponse;
import com.example.ingressjobs.enums.ExperienceLevel;
import com.example.ingressjobs.enums.JobType;
import com.example.ingressjobs.scrap.DjinniScraperService;
import com.example.ingressjobs.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final DjinniScraperService djinniScraperService;


    @ConsoleLog("job added")
    @PostMapping("/add-job")
    @ResponseStatus(HttpStatus.CREATED)
    public void addJob(@RequestBody JobRequest jobRequest) {
        jobService.addJob(jobRequest);
    }

    @ConsoleLog("job updated")
    @PutMapping("/update-job-by-id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateJobByID(@RequestBody JobRequest jobRequest, @PathVariable Long id) {
        jobService.updateJobByID(jobRequest, id);
    }

    @ConsoleLog("job updated")
    @PatchMapping("/update-job-by-id-partial/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateJobByIDPartial(@RequestBody JobRequest jobRequest, @PathVariable Long id) {
        jobService.updateJobByID(jobRequest, id);
    }

    @GetMapping("/get-job-by-id")
    @ResponseStatus(HttpStatus.OK)
    public JobResponse getJobByID(@RequestParam Long id) {
        return jobService.getJobByID(id);
    }

    @GetMapping("/get-all-jobs")
    @ResponseStatus(HttpStatus.OK)
    public Page<JobResponse> getAllJobs(@PageableDefault(value = 12) Pageable pageable) {
        return jobService.getAllJobs(pageable);
    }


    @ConsoleLog("job deleted")
    @DeleteMapping("/delete-job-by-id")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteJobByID(@RequestParam Long id) {
        jobService.deleteJobByID(id);
    }


    @GetMapping("/sort-jobs-by-salary-range-ascending")
    @ResponseStatus(HttpStatus.OK)
    public Page<JobResponse> sortJobsBySalaryRangeAscending(@PageableDefault(value = 12) Pageable pageable) {
        return jobService.sortJobsBySalaryRangeAscending(pageable);
    }

    @GetMapping("/sort-jobs-by-salary-range-descending")
    @ResponseStatus(HttpStatus.OK)
    public Page<JobResponse> sortJobsBySalaryRangeDescending(@PageableDefault(value = 12) Pageable pageable) {
        return jobService.sortJobsBySalaryRangeDescending(pageable);
    }

    @GetMapping("/sort-jobs-by-posted-date-ascending")
    @ResponseStatus(HttpStatus.OK)
    public Page<JobResponse> sortJobsByPostedDateAscending(@PageableDefault(value = 12) Pageable pageable) {
        return jobService.sortJobsByPostedDateAscending(pageable);
    }

    @GetMapping("/sort-jobs-by-posted-date-descending")
    @ResponseStatus(HttpStatus.OK)
    public Page<JobResponse> sortJobsByPostedDateDescending(@PageableDefault(value = 12) Pageable pageable) {
        return jobService.sortJobsByPostedDateDescending(pageable);
    }

    @GetMapping("/find-job-by-spec")
    @ResponseStatus(HttpStatus.OK)
    public Page<JobResponse> findBySpecification(@RequestParam(required = false) String jobTittle,
                                                 @RequestParam(required = false) String companyName,
                                                 @RequestParam(required = false) List<String> location,
                                                 @RequestParam(required = false) JobType jobType,
                                                 @RequestParam(required = false) ExperienceLevel experienceLevel,
                                                 @RequestParam(required = false) LocalDate postedDate,
                                                 @RequestParam(required = false) String tags,
                                                 @PageableDefault(value = 12) Pageable pageable) {

        return jobService.findBySpecification(jobTittle, companyName, location, jobType,
                experienceLevel, postedDate, tags, pageable);
    }

    @PostMapping("/api/jobs/scrape")
    public ResponseEntity<String> scrapeJobs() {
        try {
            djinniScraperService.scrapeAndSaveJobs();
            return ResponseEntity.ok("Jobs scraped successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during scraping: " + e.getMessage());
        }
    }


}
