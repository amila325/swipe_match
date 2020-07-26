package com.swipejobs.matcher.filters;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.Worker;
import com.swipejobs.matcher.exceptions.JobNotFoundException;
import com.swipejobs.matcher.exceptions.WorkerNotFoundException;

import java.util.List;
import java.util.Objects;

public class JobMatchFilter {

    private FilterChain filterChain;
    private Worker worker;
    private List<Job> jobList;

    private JobMatchFilter(FilterChain filterChain, Worker worker, List<Job> jobList) {
        this.filterChain = filterChain;
        this.worker = worker;
        this.jobList = jobList;
    }

    public static class Builder {

        private Worker worker;
        private List<Job> jobList;

        public Builder withWorker(Worker worker) {
            this.worker = worker;
            return this;
        }

        public Builder withJobList(List<Job> jobList) {
            this.jobList = jobList;
            return this;
        }

        public JobMatchFilter build() {
            FilterChain filterChain = new FilterChain();
            filterChain.addFilter(new GeoSpatialFilter());
            filterChain.addFilter(new CertificateFilter());
            filterChain.addFilter(new MaxRecordFilter());
            if (Objects.isNull(worker)) {
                throw new WorkerNotFoundException();
            }
            if (Objects.isNull(jobList)) {
                throw new JobNotFoundException();
            }
            JobMatchFilter matchFilter = new JobMatchFilter(filterChain, worker, jobList);
            return matchFilter;
        }

    }

    public List<Job> getFilteredJobs() {

        return filterChain.execute(worker, jobList);
    }

}

