package com.microservice.allocationservice.controller;

import com.microservice.allocationservice.model.Allocation;
import com.microservice.allocationservice.model.Employee;
import com.microservice.allocationservice.model.Project;
import com.microservice.allocationservice.service.AllocationService;
import com.microservice.allocationservice.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// http://localhost:8090/swagger-ui.html

@Api(value = "Swagger - 2 AllocationController")
@RestController
@RequestMapping("api/allocation")
public class AllocationController {

    @Autowired
    AllocationService service;

    /**
     * Allocate the Project to Employee
     * @param allocation - Allocation Records
     * @return - Saved Allocation Record
     */
    @ApiOperation(value = "Allocate the Project to Employee",
            response = Allocation.class, tags = "Allotment")
    @PostMapping("/addHrs")
    public Allocation saveAllocation(@RequestBody Allocation allocation) throws Exception {
        return service.saveAllocation(allocation);
    }

    /**
     * Get Allocation Record BY Id
     * @param id - Allotment Id
     * @return - Allocation
     */
    @ApiOperation(value = "Get Allotment with Projects and Employees Details",
            response = ResponseVo.class, tags = "Allotment")
    @GetMapping("/{id}")
    public ResponseVo findAllotmentById(@PathVariable Integer id){
        return service.findAllotmentById(id);
    }

    @ApiOperation(value = "Get all alloment details",
            response = ResponseVo.class, tags = "Allotment")
    @GetMapping("/all")
    public List<ResponseVo> getAllAllotements(){
        return service.getAll();
    }
    /**
     * Update Allocation to an Employee
     * @param allocation - Allocation Records
     * @return - Updated Allocation Record
     */
    @ApiOperation(value = "Update Allocation to an Employee",
            response = Allocation.class, tags = "Allotment")
    @PutMapping("/updateHrs")
    public Allocation updateAllocation(@RequestBody Allocation allocation){
        return service.updateAllocation(allocation);
    }

    /**
     * delete allocation
     * @param id - Allocation Id
     * @return - Deleted Message
     */
    @ApiOperation(value = "Remove the Employee From Allocation",
            response = String.class, tags = "Allotment")
    @DeleteMapping("/{id}")
    public String deleteAllocationById(@PathVariable Integer id){
        boolean status = service.deleteAllocationById(id);
        if(status){
            return "Allocation with id "+id+" deleted.";
        }else {
            return "Allocation with id "+id+" not deleted.";
        }
    }

    /**
     * get all project details of an employee
     * @param empId
     */
    @ApiOperation(value = "get all project details of an employee",
            response = List.class, tags = "Allotment")
    @GetMapping("/projects/{empId}")
    public List<Project> getProjects(@PathVariable Integer empId){
        return service.getProjects(empId);
    }

    /**
     * get all employee details of a project
     * @param projectId
     */
    @ApiOperation(value = "get all employee details of a project",
            response = List.class, tags = "Allotment")
    @GetMapping("/employees/{projectId}")
    public List<Employee> getEmployees(@PathVariable Integer projectId){
        return service.getEmployees(projectId);
    }

    /**
     * @param id - Employee id
     * @return - All Mapped Projects Ids
     */
    @ApiOperation(value = "Get All Mapped Project Ids for Single Employee",
            response = ResponseEntity.class, tags = "Allotment")
    @GetMapping("/projectIds/{id}")
    public ResponseEntity<int[]> getProjectIds(@PathVariable Integer id){

        return service.getProjectIds(id);
    }
}
