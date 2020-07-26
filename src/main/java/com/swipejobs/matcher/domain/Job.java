package com.swipejobs.matcher.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job implements Comparable<Job>{

    private Boolean driverLicenseRequired;
    private List<String> requiredCertificates = null;
    private Location location;
    private String billRate;
    private Integer workersRequired;
    private Date startDate;
    private String about;
    private String jobTitle;
    private String company;
    private String guid;
    private Integer jobId;

    @Override
    public int compareTo(Job comparingJob) {
        if(getStartDate() == null || comparingJob.getStartDate() == null){
            return 0;
        }
        return getStartDate().compareTo(comparingJob.getStartDate());
    }

}
