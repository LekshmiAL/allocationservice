package com.microservice.allocationservice.repository;

import com.microservice.allocationservice.model.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AllocationRepository extends JpaRepository<Allocation,Integer> {

    Allocation findByAllocationId(Integer id);

    @Query(value = "select a.projectId from Allocation a where a.employeeId in ?1")
    List<Integer> findAllProjectIdByEmpId(Integer empId);

    @Query(value = "select a.employeeId from Allocation a where a.projectId in ?1")
    List<Integer> findAllEmployeeIdsByPid(Integer projectIds);

}
