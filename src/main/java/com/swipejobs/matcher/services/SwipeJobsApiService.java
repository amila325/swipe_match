package com.swipejobs.matcher.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.Worker;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SwipeJobsApiService {

    private final String WORKER_URL;
    private final String JOB_URL;
    private final OkHttpClient httpClient;
    private final Gson gson;


    public SwipeJobsApiService(@Value("${swipejob.url.workers}") final String worker_url,
                               @Value("${swipejob.url.jobs}") final String job_url) {
        this.WORKER_URL = worker_url;
        this.JOB_URL = job_url;
        this.httpClient = new OkHttpClient();
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    }

    public List<Job> retrieveJobs(){
        List<Job> jobs = new ArrayList<>();
        try {
            Request request = new Request.Builder().url(JOB_URL).get().build();

            Response response = httpClient.newCall(request).execute();
            String jobString = response.body().string();
            Job[] jobArray = gson.fromJson(jobString, Job[].class);

            jobs = Arrays.asList(jobArray);
        }catch (Exception ex){
            log.error("Error while trying to access jobs api", ex);
        }
        return jobs;
    }

    public Map<Integer, Worker> retrieveWorkers(){
        Map<Integer, Worker> workers = new HashMap<>();
        try{
            Request request = new Request.Builder().url(WORKER_URL).get().build();

            Response response = httpClient.newCall(request).execute();
            String workerString = response.body().string();
            Worker[] workerList = gson.fromJson(workerString, Worker[].class);

            workers = convertWorkersToIdMap(workerList);
        }catch (Exception ex){
            log.error("Error while trying to access worker api", ex);
        }

        return workers;
    }

    private Map<Integer, Worker> convertWorkersToIdMap(Worker [] workers){
        Map<Integer, Worker> workerMap = Arrays.stream(workers)
                .collect(Collectors.toMap(Worker::getUserId, worker -> worker));
        return workerMap;
    }

}
