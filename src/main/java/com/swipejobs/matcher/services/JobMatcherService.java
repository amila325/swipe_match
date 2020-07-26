package com.swipejobs.matcher.services;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.Worker;
import com.swipejobs.matcher.exceptions.WorkerNotFoundException;
import com.swipejobs.matcher.filters.JobMatchFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class JobMatcherService {


    private final SwipeJobsApiService swipeJobsApiService;

    //Normally a service class should be stateless, but for this POC it's just cached here when the bean instantiated
    private List<Job> jobs;
    private Map<Integer, Worker> workerMap;

    @Autowired
    public JobMatcherService(final SwipeJobsApiService swipeJobsApiService) {
        this.swipeJobsApiService = swipeJobsApiService;
    }

    private void retrieveData() {
        workerMap = swipeJobsApiService.retrieveWorkers();
        jobs = swipeJobsApiService.retrieveJobs();
    }

    public List<Job> getMatchingJobs(final Integer workerId) {
        retrieveData();
        final Worker worker = workerMap.get(workerId);
        if (Objects.isNull(worker)) {
            log.debug("Matching worker couldn't be found for worker id: {}", workerId);
            throw new WorkerNotFoundException(workerId);
        }

        JobMatchFilter matchFilter = new JobMatchFilter.Builder().withWorker(worker).withJobList(jobs).build();
        List<Job> matchingJobs = matchFilter.getFilteredJobs();

        return matchingJobs;
    }

}

