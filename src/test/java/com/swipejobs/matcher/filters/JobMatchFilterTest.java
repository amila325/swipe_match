package com.swipejobs.matcher.filters;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.Worker;
import com.swipejobs.matcher.exceptions.JobNotFoundException;
import com.swipejobs.matcher.exceptions.WorkerNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JobMatchFilterTest {

    private List<Job> jobs = new ArrayList<>();
    private Worker worker = new Worker();

    @Test
    public void test_SuccessJobMatchFilterCreation(){
        JobMatchFilter matchFilter = new JobMatchFilter.Builder().withWorker(worker).withJobList(jobs).build();
        Assertions.assertThat(matchFilter).isNotNull();
    }

    @Test
    public void test_WorkerNotFoundException(){
        Assertions.assertThatExceptionOfType(WorkerNotFoundException.class).isThrownBy(() ->
                new JobMatchFilter.Builder().withJobList(jobs).build());
    }

    @Test
    public void test_JobNotFoundException(){
        Assertions.assertThatExceptionOfType(JobNotFoundException.class).isThrownBy(() ->
                new JobMatchFilter.Builder().withWorker(worker).build());
    }

}
