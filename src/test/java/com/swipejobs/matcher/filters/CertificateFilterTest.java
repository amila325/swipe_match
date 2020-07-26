package com.swipejobs.matcher.filters;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.Worker;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CertificateFilterTest {

    private List<Job> jobs;
    private CertificateFilter certificateFilter;
    private final String [] workerFewCerts = new String[]{"Cert1", "Cert2", "Cert5"};
    private final String [] jobCerts = new String[]{"Cert1", "Cert2", "Cert3"};
    private final String [] extraJobCerts = new String[]{"Cert4", "Cert5", "Cert6"};

    @Before
    public void setup(){
        certificateFilter = new CertificateFilter();
        List<String> jobCertificates = Arrays.asList(jobCerts);
        List<String> extraJobCertificates = new ArrayList<>(Arrays.asList(jobCerts));
        extraJobCertificates.addAll(Arrays.asList(extraJobCerts));

        Job job1 = new Job();
        job1.setRequiredCertificates(jobCertificates);
        job1.setGuid(UUID.randomUUID().toString());
        job1.setJobId(1);

        Job job2 = new Job();
        job2.setRequiredCertificates(jobCertificates);
        job2.setGuid(UUID.randomUUID().toString());
        job1.setJobId(2);

        Job job3 = new Job();
        job3.setRequiredCertificates(extraJobCertificates);
        job3.setGuid(UUID.randomUUID().toString());
        job1.setJobId(3);

        Job job4 = new Job();
        job4.setRequiredCertificates(extraJobCertificates);
        job4.setGuid(UUID.randomUUID().toString());
        job1.setJobId(4);

        jobs = Arrays.asList(job1, job2, job3, job4);
    }

    @Test
    public void test_WorkerWithAllCertsForJobs(){
        Worker worker = new Worker();
        List<String> fullCertList = new ArrayList<>(Arrays.asList(jobCerts));
        fullCertList.addAll(Arrays.asList(extraJobCerts));
        worker.setCertificates(fullCertList);
        worker.setUserId(1);
        List<Job> matchingJobs = certificateFilter.execute(worker, jobs);
        Assertions.assertThat(matchingJobs).isNotNull();
        Assertions.assertThat(matchingJobs).isNotEmpty();
        Assertions.assertThat(matchingJobs.size()).isEqualTo(jobs.size());

    }

    @Test
    public void test_FewMatchingJobsForWorker(){
        Worker worker = new Worker();
        List<String> halfCertList = new ArrayList<>(Arrays.asList(jobCerts));
        worker.setCertificates(halfCertList);
        worker.setUserId(1);
        List<Job> matchingJobs = certificateFilter.execute(worker, jobs);
        Assertions.assertThat(matchingJobs).isNotNull();
        Assertions.assertThat(matchingJobs).isNotEmpty();
        Assertions.assertThat(matchingJobs.size()).isEqualTo(2);
    }

    @Test
    public void test_WorkerWithNoCerts(){
        Worker worker = new Worker();
        List<String> emptyCertList = new ArrayList<>();
        worker.setCertificates(emptyCertList);
        worker.setUserId(1);
        List<Job> matchingJobs = certificateFilter.execute(worker, jobs);
        Assertions.assertThat(matchingJobs).isNotNull();
        Assertions.assertThat(matchingJobs).isEmpty();
    }

    @Test
    public void test_WorkerWithFewMatchingCerts(){
        Worker worker = new Worker();
        List<String> fewCertList = new ArrayList<>(Arrays.asList(workerFewCerts));
        worker.setCertificates(fewCertList);
        worker.setUserId(1);
        List<Job> matchingJobs = certificateFilter.execute(worker, jobs);
        Assertions.assertThat(matchingJobs).isNotNull();
        Assertions.assertThat(matchingJobs).isEmpty();
    }

}
