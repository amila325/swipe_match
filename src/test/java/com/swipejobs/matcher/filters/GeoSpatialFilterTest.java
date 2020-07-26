package com.swipejobs.matcher.filters;


import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.JobSearchAddress;
import com.swipejobs.matcher.domain.Location;
import com.swipejobs.matcher.domain.Worker;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GeoSpatialFilterTest {

    private List<Job> jobs;
    private Worker worker;
    private Worker globalWorker;
    private GeoSpatialFilter geoSpatialFilter;

    @Before
    public void setup(){
        geoSpatialFilter = new GeoSpatialFilter();
        List<String> certificates = Arrays.asList("Cert1", "Cert2", "Cert3");
        Location jobLocation1 = new Location(10.0, 10.0);
        Location jobLocation2 = new Location(-10.0, -10.0);

        Job job1 = new Job();
        job1.setLocation(jobLocation1);
        job1.setRequiredCertificates(certificates);
        job1.setGuid(UUID.randomUUID().toString());
        job1.setJobId(1);

        Job job2 = new Job();
        job2.setLocation(jobLocation1);
        job2.setRequiredCertificates(certificates);
        job2.setGuid(UUID.randomUUID().toString());
        job1.setJobId(2);

        Job job3 = new Job();
        job3.setLocation(jobLocation2);
        job3.setRequiredCertificates(certificates);
        job3.setGuid(UUID.randomUUID().toString());
        job1.setJobId(3);

        Job job4 = new Job();
        job4.setLocation(jobLocation2);
        job4.setRequiredCertificates(certificates);
        job4.setGuid(UUID.randomUUID().toString());
        job1.setJobId(4);

        worker = new Worker();
        JobSearchAddress address = new JobSearchAddress("km", 10, 10.0, 10.0 );
        worker.setJobSearchAddress(address);
        worker.setCertificates(certificates);
        worker.setUserId(1);

        globalWorker = new Worker();
        JobSearchAddress globalAddress = new JobSearchAddress("km", 20038, 0.0, 0.0 );
        globalWorker.setJobSearchAddress(globalAddress);
        globalWorker.setCertificates(certificates);
        globalWorker.setUserId(1);

        jobs = Arrays.asList(job1, job2, job3, job4);
    }

    @Test
    public void test_GeoFilter(){
        List<Job> geoFilteredJobs = geoSpatialFilter.execute(worker, jobs);
        Assertions.assertThat(geoFilteredJobs.size()).isEqualTo(2);
    }

    @Test
    public void test_GeoFilterEmptyJobList(){
        List<Job> geoFilteredJobs = geoSpatialFilter.execute(worker, new ArrayList<>());
        Assertions.assertThat(geoFilteredJobs.size()).isEqualTo(0);
    }

    @Test
    public void test_GeoFilterAllMatching(){
        List<Job> geoFilteredJobs = geoSpatialFilter.execute(globalWorker, jobs);
        Assertions.assertThat(geoFilteredJobs.size()).isEqualTo(jobs.size());
    }

}
