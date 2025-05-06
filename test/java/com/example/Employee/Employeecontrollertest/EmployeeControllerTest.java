package com.example.Employee.Employeecontrollertest;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;

import com.example.Employee.Employeecontroller.EmployeeController;
import com.example.Employee.Employeedto.EmployeeDTO;
import com.example.Employee.Employeeservice.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
	
	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private EmployeeService employeeservice;

	    @Autowired
	    private ObjectMapper objectMapper;
	    
	    
	    // to test save or update 

	    @Test
	    public void testSaveOrUpdateEmployee() throws Exception {
	        // Given: A mock input DTO
	        EmployeeDTO inputDTO = new EmployeeDTO();
	        inputDTO.setId(1L);
	        inputDTO.setName("Alice");

	        // When: Mocking the service return
	        Mockito.when(employeeservice.saveOrUpdateEmployee(any(EmployeeDTO.class)))
	               .thenReturn(inputDTO);

	        // Then: Perform and verify
	        mockMvc.perform(post("/employee/save")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(inputDTO)))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.id").value(1L))
	                .andExpect(jsonPath("$.name").value("Alice"));
	    }
	    
	    // to get by id
	    
	    @Test
	    public void testGetEmployeeById() throws Exception {
	        // Given: A sample EmployeeDTO
	        EmployeeDTO mockEmployee = new EmployeeDTO();
	        mockEmployee.setId(1L);
	        mockEmployee.setName("John Doe");

	        // When: Mocking the service method
	        Mockito.when(employeeservice.GetemployeebtId(anyLong()))
	               .thenReturn(mockEmployee);

	        // Then: Perform the GET request and assert the response
	        mockMvc.perform(get("/employee/get/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.id").value(1L))
	                .andExpect(jsonPath("$.name").value("John Doe"));
	    }
	    
	    // to get all
	    
	    @Test
	    public void testGetAllEmployees() throws Exception {
	        // Sample employee list
	        EmployeeDTO emp1 = new EmployeeDTO();
	        emp1.setId(1L);
	        emp1.setName("Alice");

	        EmployeeDTO emp2 = new EmployeeDTO();
	        emp2.setId(2L);
	        emp2.setName("Bob");

	        List<EmployeeDTO> employeeList = Arrays.asList(emp1, emp2);

	        // Mock the service
	        when(employeeservice.getAllEmployee()).thenReturn(employeeList);

	        // Perform GET request and validate
	        mockMvc.perform(get("/employee/get/all")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.length()").value(2))
	                .andExpect(jsonPath("$[0].id").value(1L))
	                .andExpect(jsonPath("$[0].name").value("Alice"))
	                .andExpect(jsonPath("$[1].id").value(2L))
	                .andExpect(jsonPath("$[1].name").value("Bob"));
	    }
	    
	    // to delete employee 
	    
	    @Test
	    public void testDeleteEmployee_Success() throws Exception {
	        Long employeeId = 1L;
	        String successMessage = "Employee deleted successfully";

	        // Mock service response
	        when(employeeservice.deleteEmployeeBySoftDeleting(employeeId)).thenReturn(successMessage);

	        mockMvc.perform(post("/employee/delete/{id}", employeeId)
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(content().string(successMessage));
	    }

	    @Test
	    public void testDeleteEmployee_NotFound() throws Exception {
	        Long employeeId = 99L;
	        String errorMessage = "Employee not found";

	        // Mock service response
	        when(employeeservice.deleteEmployeeBySoftDeleting(employeeId)).thenReturn(errorMessage);

	        mockMvc.perform(post("/employee/delete/{id}", employeeId)
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNotFound())
	                .andExpect(content().string(errorMessage));
	    }

}
