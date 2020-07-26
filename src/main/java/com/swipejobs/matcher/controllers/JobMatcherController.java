package com.swipejobs.matcher.controllers;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.services.JobMatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobMatcherController {

    @Autowired
    JobMatcherService jobService;

    @GetMapping("/match/{workerId}")
    public List<Job> match(@PathVariable Integer workerId){
        return jobService.getMatchingJobs(workerId);
    }

}
