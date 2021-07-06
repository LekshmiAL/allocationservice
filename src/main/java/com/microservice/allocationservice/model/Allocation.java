package com.microservice.allocationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Allocation {

    @Id
    @GeneratedValue
    private int allocationId;
    private String contributionHrs;
    private int employeeId;
    private int projectId;
}
