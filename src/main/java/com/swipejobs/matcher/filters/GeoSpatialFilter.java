package com.swipejobs.matcher.filters;

import com.swipejobs.matcher.domain.Job;
import com.swipejobs.matcher.domain.Worker;
import com.swipejobs.matcher.util.DistanceCalculator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This will filter jobs according to the workers given location and distance from the given location.
 * This can be done in a mongodb geospatial query if the data is stored in a one.
 */
public class GeoSpatialFilter implements Filter {

    @Override
    public List<Job> execute(Worker worker, List<Job> matchingJobs) {

        return matchingJobs.stream()
                .filter(job ->
                        isJobWithinWorkerPreferredDistance(worker, job))
                .collect(Collectors.toList());
    }

    private boolean isJobWithinWorkerPreferredDistance(Worker worker, Job job) {
        DistanceCalculator distanceCalculator = new DistanceCalculator();

        Integer workerMaxDistance = worker.getJobSearchAddress().getMaxJobDistance();
        Double distanceFromWork = distanceCalculator.distance(
                worker.getJobSearchAddress().getLatitude(), worker.getJobSearchAddress().getLongitude(),
                job.getLocation().getLatitude(), job.getLocation().getLongitude(),
                worker.getJobSearchAddress().getUnit());

        return distanceFromWork <= workerMaxDistance;
    }

}
