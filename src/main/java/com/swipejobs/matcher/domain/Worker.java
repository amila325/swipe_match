package com.swipejobs.matcher.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Worker {

    private Integer rating;
    private Boolean isActive;
    private List<String> certificates = null;
    private List<String> skills = null;
    private JobSearchAddress jobSearchAddress;
    private String transportation;
    private Boolean hasDriversLicense;
    private List<Availability> availability = null;
    private String phone;
    private String email;
    private Name name;
    private Integer age;
    private String guid;
    private Integer userId;

}
