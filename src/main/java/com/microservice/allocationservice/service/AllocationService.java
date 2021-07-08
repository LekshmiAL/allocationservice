package com.microservice.allocationservice.service;

import com.microservice.allocationservice.model.Allocation;
import com.microservice.allocationservice.model.Employee;
import com.microservice.allocationservice.model.Project;
import com.microservice.allocationservice.vo.ResponseVo;
import org.springframework.http.ResponseEntity;

import java.net.CacheResponse;
import java.util.List;

public interface AllocationService {

    Allocation saveAllocation(Allocation allocation) throws Exception;

    Allocation updateAllocation(Allocation allocation);

    boolean deleteAllocationById(Integer id);

    ResponseVo findAllotmentById(Integer id);

    List<Project> getProjects(Integer empId);

    List<Employee> getEmployees(Integer projectId);

    List<ResponseVo> getAll();

    ResponseEntity<int[]> getProjectIds(Integer id);

    ResponseEntity<int[]> getEmpIds(Integer id);
}


