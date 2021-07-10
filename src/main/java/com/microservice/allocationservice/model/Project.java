package com.microservice.allocationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private int projectId;
    private String projectName;
    private String clientName;
    private String durationInDays;
    private Date startDate;
    private Date endDate;
}
