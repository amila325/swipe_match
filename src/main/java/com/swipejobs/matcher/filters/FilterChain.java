package com.swipejobs.matcher.filters;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class FilterChain {

    private List<Filter> filters = new ArrayList<>();

    public void addFilter(Filter filter){
        filters.add(filter);
    }

    public List<Job> execute(final Worker worker, final List<Job> matchingJobs){
        List<Job> filteredJobs = new ArrayList<>();
        if(Objects.isNull(matchingJobs)){
            return filteredJobs;
        }
        filteredJobs.addAll(matchingJobs);
        if(filteredJobs.size()==0){
            return filteredJobs;
        }
        //Each filter will be filtering matching jobs for a given worker
        for(Filter filter: filters){
            //Once filtered jobs reach 0 before the end of filter chain, it's no use to keep filtering.
            if(filteredJobs.size() == 0){
                return filteredJobs;
            }
            filteredJobs = filter.execute(worker, filteredJobs);
        }

        return filteredJobs;
    }

}
