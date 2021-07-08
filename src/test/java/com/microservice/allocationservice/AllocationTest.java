package com.microservice.allocationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.allocationservice.controller.AllocationController;
import com.microservice.allocationservice.model.Allocation;
import com.microservice.allocationservice.model.Employee;
import com.microservice.allocationservice.model.Project;
import com.microservice.allocationservice.repository.AllocationRepository;
import com.microservice.allocationservice.service.AllocationService;
import com.microservice.allocationservice.vo.ResponseVo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AllocationTest {

    @MockBean
    private AllocationService service;

    @MockBean
    private AllocationRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc ;

    @Test
    public void allocationModelTest(){
        Allocation allocation = Mockito.mock(Allocation.class);
        assertNotNull(allocation);
    }

    @Test
    public void employeeControllerTest(){
        AllocationController allocController = Mockito.mock(AllocationController.class);
        assertNotNull(allocController);
    }

    @Test
    public void saveAllocationTest() throws Exception{
        Allocation allocation = new Allocation(11, "5", 1, 2);
        Mockito.when(service.saveAllocation(ArgumentMatchers.any())).thenReturn(allocation);
        String jsonContent = mapper.writeValueAsString(allocation);

        mockMvc.perform(post("/api/allocation/addHrs")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonContent)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.allocationId", Matchers.equalTo(11)));
    }

    @Test
    public void findAllotmentByIdTest() throws Exception{
        Allocation allocation = new Allocation(11, "5", 1, 2);
        Employee emp = new Employee(101,"Janvi","koul","Developer","janvi@gmail.com","7998467362",
                LocalDate.of(2019,05,20));
        Project project = new Project(110, "dell","Application","dell client",
                new SimpleDateFormat("dd/MM/yyyy").parse("19/10/2020"),
                new SimpleDateFormat("dd/MM/yyyy").parse("19/09/2021"));
        ResponseVo responseVo = new ResponseVo(allocation, emp, project);

        Mockito.when(service.findAllotmentById(ArgumentMatchers.any())).thenReturn(responseVo);
        String jsonContent = mapper.writeValueAsString(responseVo);

        mockMvc.perform(get("/api/allocation/11")).andExpect(status().isOk())
                .andExpect(jsonPath("$.allotment.allocationId",Matchers.equalTo(11)))
                .andExpect(jsonPath("$.employee.firstName",Matchers.equalTo("Janvi")))
                .andExpect(jsonPath("$.project.projectName",Matchers.equalTo("dell")));

    }

    @Test
    public void getProjectsTest() throws Exception{
        List<Project> projectList = new ArrayList<>();
        projectList.add(new Project(110, "dell","Application","dell client",
                new SimpleDateFormat("dd/MM/yyyy").parse("19/10/2020"),
                new SimpleDateFormat("dd/MM/yyyy").parse("19/09/2021")));
        projectList.add(new Project(111, "apple","Application","Apple client",
                new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2020"),
                new SimpleDateFormat("dd/MM/yyyy").parse("03/12/2021")));
        Mockito.when(service.getProjects(ArgumentMatchers.any())).thenReturn(projectList);
        String jsonContent = mapper.writeValueAsString(projectList);
        mockMvc.perform(get("/api/allocation/projects/101")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].projectId",Matchers.equalTo(110)))
                .andExpect(jsonPath("$[1].projectId",Matchers.equalTo(111)));
    }

    @Test
    public void getEmployeesTest() throws Exception{
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(101,"Janvi","koul","Developer",
                "janvi@gmail.com","7998467362", LocalDate.of(2019,05,20)));
        employeeList.add(new Employee(102,"Micheal","Loui","HR",
                "loui@gmail.com","9994447362", LocalDate.of(2020,02,10)));
        Mockito.when(service.getEmployees(ArgumentMatchers.any())).thenReturn(employeeList);
        String jsonContent = mapper.writeValueAsString(employeeList);
        mockMvc.perform(get("/api/allocation/employees/110")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id",Matchers.equalTo(101)))
                .andExpect(jsonPath("$[1].id",Matchers.equalTo(102)));
    }
}
