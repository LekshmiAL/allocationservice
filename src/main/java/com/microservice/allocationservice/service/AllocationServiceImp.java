package com.microservice.allocationservice.service;

import com.microservice.allocationservice.model.Allocation;
import com.microservice.allocationservice.model.Employee;
import com.microservice.allocationservice.model.Project;
import com.microservice.allocationservice.repository.AllocationRepository;
import com.microservice.allocationservice.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllocationServiceImp implements AllocationService{

    @Autowired
    AllocationRepository repository;

    @Autowired
    RestTemplate restTemplate;

    public Allocation getAllocationById(Integer allocId){
        return repository.findByAllocationId(allocId);
    }

    @Override
    public Allocation saveAllocation(Allocation allocation) throws Exception{
        List<Integer> projectIds = findAllProjectIdByEmpId(allocation.getEmployeeId());
        if(projectIds.contains(allocation.getProjectId())){
            throw new Exception("Employee Already Mapped this Project");
        }
        return repository.save(allocation);
    }

    @Override
    public Allocation updateAllocation(Allocation allocation) {
        return repository.save(allocation);
    }

    @Override
    public boolean deleteAllocationById(Integer id) {
        boolean returnStatus = false;
        if(getAllocationById(id)!=null){
            repository.deleteById(id);
            returnStatus = true;
        }
        return returnStatus;
    }

    @Override
    public ResponseVo findAllotmentById(Integer id) {
        Allocation allocation = getAllocationById(id);
        return getResponseVo(allocation);
    }

    @Override
    public  List<Project> getProjects(Integer empId) {
        List<Integer> pids = findAllProjectIdByEmpId(empId);
        List<Project> projectList = new ArrayList<>();
        pids.forEach(pid-> {
            Project project = restTemplate.getForObject("http://localhost:8092/api/project/"+pid, Project.class);
            projectList.add(project);
        });
        return projectList;
    }

    @Override
    public List<Employee> getEmployees(Integer projectId) {
        List<Integer> eids = findAllEmployeeIdsByPid(projectId);
        List<Employee> employeeList = new ArrayList<>();
        eids.forEach(eid -> {
            Employee employee = restTemplate.getForObject("http://localhost:8091/api/employee/"+eid, Employee.class);
            employeeList.add(employee);
        });
        return  employeeList;
    }

    @Override
    public List<ResponseVo> getAll() {
        List<ResponseVo> responseVoList = new ArrayList<>();
        List<Allocation> allocationList = repository.findAll();
        allocationList.forEach(allocation -> {
            ResponseVo responseVo = getResponseVo(allocation);
            responseVoList.add(responseVo);
        });
        return responseVoList;
    }

    /**
     * get Employee detail and project details of an allocation
     * @param allocation - allocation details
     * @return ResponseVo
     */
    public ResponseVo getResponseVo(Allocation allocation){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setAllotment(allocation);
        int eid = allocation.getEmployeeId();
        int pid = allocation.getProjectId();
        Employee employee = restTemplate.getForObject("http://localhost:8091/api/employee/"+eid, Employee.class);
        responseVo.setEmployee(employee);
        Project project = restTemplate.getForObject("http://localhost:8092/api/project/"+pid, Project.class);
        responseVo.setProject(project);
        return responseVo;
    }


    @Override
    public ResponseEntity<int[]> getProjectIds(Integer empId){
        ResponseEntity<int[]> responseEntity = null;
        int[] ids;
        List<Integer> listIds = findAllProjectIdByEmpId(empId);
        if(!listIds.isEmpty()){
            ids = new int[listIds.size()];
            ids =   listIds.stream().mapToInt(Integer::intValue).toArray();
            responseEntity = new ResponseEntity<>(ids, HttpStatus.ACCEPTED);
        }

        return responseEntity;
    }

    @Override
    public ResponseEntity<int[]> getEmpIds(Integer projectId){
        ResponseEntity<int[]> responseEntity = null;
        int[] ids;
        List<Integer> listIds = findAllEmployeeIdsByPid(projectId);

        if(!listIds.isEmpty()){
            ids = new int[listIds.size()];
            ids =   listIds.stream().mapToInt(Integer::intValue).toArray();
            responseEntity = new ResponseEntity<>(ids, HttpStatus.ACCEPTED);
        }

        return responseEntity;
    }

    /***
     * find all projects id mapped to an EmpId
     * @param empId
     * @return
     */
    public List<Integer> findAllProjectIdByEmpId(int empId){
        return repository.findAllProjectIdByEmpId(empId);
    }

    /**
     * find all employee id mapped to a ProjectId
     * @param pid
     * @return
     */
    public List<Integer> findAllEmployeeIdsByPid(int pid){
        return repository.findAllEmployeeIdsByPid(pid);
    }
}
