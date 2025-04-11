package com.example.ingressjobs.service;

import com.example.ingressjobs.dto.request.JobRequest;
import com.example.ingressjobs.dto.response.JobResponse;
import com.example.ingressjobs.exception.JobNotFoundException;
import com.example.ingressjobs.mapper.JobMapper;
import com.example.ingressjobs.model.Job;
import com.example.ingressjobs.repository.JobRepository;
import com.example.ingressjobs.service.impl.JobServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class JobServiceImplTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobMapper jobMapper;

    @InjectMocks
    private JobServiceImpl jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddJob_success() {
        // given
        JobRequest jobRequest = new JobRequest();
        Job job = new Job();

        when(jobMapper.toJobEntity(jobRequest)).thenReturn(job);

        // when
        jobService.addJob(jobRequest);

        // then
        verify(jobMapper, times(1)).toJobEntity(jobRequest);
        verify(jobRepository, times(1)).save(job);
    }

    @Test
    void testUpdateJobByID_success() {
        // given
        Long jobId = 1L;
        JobRequest jobRequest = new JobRequest();
        jobRequest.setJobTittle("Backend Developer");
        jobRequest.setCompanyName("Test Company");

        Job existingJob = new Job();
        existingJob.setId(jobId);

        when(jobRepository.findById(jobId)).thenReturn(Optional.of(existingJob));

        // when
        jobService.updateJobByID(jobRequest, jobId);

        // then
        verify(jobRepository, times(1)).findById(jobId);
        verify(jobRepository, times(1)).save(existingJob);

        assertEquals("Backend Developer", existingJob.getJobTittle());
        assertEquals("Test Company", existingJob.getCompanyName());
    }

    @Test
    void testUpdateJobByID_jobNotFound() {
        Long jobId = 1L;
        JobRequest jobRequest = new JobRequest();

        when(jobRepository.findById(jobId)).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class, () -> {
            jobService.updateJobByID(jobRequest, jobId);
        });

        verify(jobRepository, times(1)).findById(jobId);
        verify(jobRepository, never()).save(any());
    }

    @Test
    void testGetJobByID_success() {
        Long jobId = 2L;
        Job job = new Job();
        JobResponse response = new JobResponse();

        when(jobRepository.findById(jobId)).thenReturn(Optional.of(job));
        when(jobMapper.toJobResponse(job)).thenReturn(response);

        JobResponse result = jobService.getJobByID(jobId);

        assertEquals(response, result);
    }

    @Test
    void testGetJobByID_notFound() {
        Long jobId = 404L;
        when(jobRepository.findById(jobId)).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class, () -> jobService.getJobByID(jobId));
    }



    @Test
    void testDeleteJobByID_success() {
        Long jobId = 10L;
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(new Job()));

        jobService.deleteJobByID(jobId);

        verify(jobRepository).deleteById(jobId);
    }

    @Test
    void testDeleteJobByID_notFound() {
        Long jobId = 10L;
        when(jobRepository.findById(jobId)).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class, () -> jobService.deleteJobByID(jobId));
    }



}
