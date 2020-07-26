package com.swipejobs.matcher.controllers;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.exceptions.WorkerNotFoundException;
import com.swipejobs.matcher.services.JobMatcherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class JobMatcherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobMatcherService jobMatcherService;

    @Test
    public void match_ShouldReturnJob() throws Exception{

        Job testJob = new Job();
        testJob.setJobTitle("Test Title");
        given(jobMatcherService.getMatchingJobs(anyInt())).willReturn(Arrays.asList(testJob));

        mockMvc.perform(MockMvcRequestBuilders.get("/match/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].jobTitle").value("Test Title"));
    }

    @Test
    public void match_NoMatchingJob() throws Exception{

        given(jobMatcherService.getMatchingJobs(anyInt())).willReturn(new ArrayList<Job>());

        mockMvc.perform(MockMvcRequestBuilders.get("/match/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void match_NoMatchingWorkerFound() throws Exception{

        given(jobMatcherService.getMatchingJobs(AdditionalMatchers.lt(0))).
                willThrow(new WorkerNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/match/-1"))
                .andExpect(status().isNotFound());
    }

}