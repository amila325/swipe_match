package com.swipejobs.matcher.filters;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.Worker;

import java.util.List;

public interface Filter {

    List<Job> execute(Worker worker, List<Job> matchingJobs);

}
