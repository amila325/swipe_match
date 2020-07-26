package com.swipejobs.matcher.filters;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.Worker;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This would filter the jobs according to the jobs' specified required certificates with workers current certificates
 */
public class CertificateFilter implements Filter {

    @Override
    public List<Job> execute(Worker worker, List<Job> matchingJobs) {
        matchingJobs = matchingJobs.stream()
                .filter(job -> worker.getCertificates().containsAll(job.getRequiredCertificates()))
                .collect(Collectors.toList());
        return matchingJobs;
    }

}
