package com.swipejobs.matcher.filters;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.Worker;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Currently this record filter would simply limit the number of records to 3 records.
 * This can be extended by having some sort of sorting strategy involved within here and then limit. Currently it
 * sorts only through the start date.
 */
public class MaxRecordFilter implements Filter {

    //Externalize or configuration per account
    private static final Integer MAX_NUMBER_OF_RECORDS = 3;

    @Override
    public List<Job> execute(Worker worker, List<Job> matchingJobs) {
        if(matchingJobs.size() < MAX_NUMBER_OF_RECORDS){
            return matchingJobs;
        }
        return matchingJobs.stream()
                .sorted()
                .limit(MAX_NUMBER_OF_RECORDS).collect(Collectors.toList());
    }

}
