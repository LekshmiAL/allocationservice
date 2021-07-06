package com.microservice.allocationservice.vo;

import com.microservice.allocationservice.model.Allocation;
import com.microservice.allocationservice.model.Employee;
import com.microservice.allocationservice.model.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVo {
    private Allocation allotment;
    private Employee employee;
    private Project project;
}
