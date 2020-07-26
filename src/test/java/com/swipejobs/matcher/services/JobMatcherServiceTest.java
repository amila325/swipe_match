package com.swipejobs.matcher.services;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.JobSearchAddress;
import com.swipejobs.matcher.domain.Location;
import com.swipejobs.matcher.domain.Worker;
import com.swipejobs.matcher.exceptions.WorkerNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JobMatcherService.class})
public class JobMatcherServiceTest {

    private JobMatcherService jobMatcherService;

    @MockBean
    private SwipeJobsApiService swipeJobsApiService;

    @Before
    public void setup(){
        List<String> certificates = Arrays.asList("Cert1", "Cert2", "Cert3");
        Job job1 = new Job();
        job1.setLocation(new Location(0.0,0.0));
        job1.setRequiredCertificates(certificates);
        job1.setGuid(UUID.randomUUID().toString());
        job1.setJobId(1);

        Job job2 = new Job();
        job2.setLocation(new Location(0.0,0.0));
        job2.setRequiredCertificates(certificates);
        job2.setGuid(UUID.randomUUID().toString());
        job1.setJobId(2);

        Job job3 = new Job();
        job3.setLocation(new Location(0.0,0.0));
        job3.setRequiredCertificates(certificates);
        job3.setGuid(UUID.randomUUID().toString());
        job1.setJobId(3);

        Job job4 = new Job();
        job4.setLocation(new Location(0.0,0.0));
        job4.setRequiredCertificates(certificates);
        job4.setGuid(UUID.randomUUID().toString());
        job1.setJobId(4);

        Worker worker = new Worker();
        JobSearchAddress address = new JobSearchAddress("km", 10, 0.0, 0.0 );
        worker.setJobSearchAddress(address);
        worker.setCertificates(certificates);
        worker.setUserId(1);

        Map<Integer, Worker> workerMap = new HashMap<>();
        workerMap.put(worker.getUserId(), worker);

        List<Job> jobs = Arrays.asList(job1, job2, job3, job4);

        given(swipeJobsApiService.retrieveWorkers()).willReturn(workerMap);
        given(swipeJobsApiService.retrieveJobs()).willReturn(jobs);
        jobMatcherService = new JobMatcherService(swipeJobsApiService);
    }

    @Test
    public void test_getMatchingJobs(){
        Assertions.assertThat(jobMatcherService.getMatchingJobs(1).size()).isEqualTo(3);
    }

    @Test
    public void test_InvalidWorkerId(){
        Assertions.assertThatExceptionOfType(WorkerNotFoundException.class).isThrownBy(() -> {
            jobMatcherService.getMatchingJobs(-1);
        });
    }

}
