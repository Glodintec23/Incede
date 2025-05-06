package com.example.Employee.Employeecontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.Employee.Employeedto.EmployeeDTO;
import com.example.Employee.Employeeservice.EmployeeService;
 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeservice;
	
	
    @PostMapping("/save")
    public EmployeeDTO saveOrUpdateEmployee(@RequestBody EmployeeDTO employeeDTO) {
         return  employeeservice.saveOrUpdateEmployee(employeeDTO);
         
    }
    
    @GetMapping("/get/{id}")
    public EmployeeDTO getEmployee(@PathVariable Long id) {
        return employeeservice.GetemployeebtId(id);
    }
    
    @GetMapping("/get/all")
    public List<EmployeeDTO> getEmployee() {
        return employeeservice.getAllEmployee();
    }
    
    
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
         String result = employeeservice.deleteEmployeeBySoftDeleting(id);
        
         if (result.contains("successfully")) {
            return ResponseEntity.ok(result);   
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);   
        }
    }



}
