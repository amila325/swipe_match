package com.swipejobs.matcher.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such worker found")
public class WorkerNotFoundException extends RuntimeException {

    private Integer workerId = -1;

    public WorkerNotFoundException(Integer workerId){
        this.workerId = workerId;
    }

    public WorkerNotFoundException(){
    }

    @Override
    public String getMessage() {
        return String.format("Worker id %d not found in the System", workerId);
    }

}
