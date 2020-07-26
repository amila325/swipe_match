package com.swipejobs.matcher.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such job found")
public class JobNotFoundException extends RuntimeException {

    private Integer jobId;

    public JobNotFoundException(Integer jobId){
        this.jobId = jobId;
    }

    public JobNotFoundException(){

    }

    @Override
    public String getMessage() {
        return String.format("Job id %d not found in the System", jobId);
    }

}
