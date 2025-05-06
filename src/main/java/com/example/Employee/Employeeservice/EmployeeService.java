package com.example.Employee.Employeeservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Employee.Employeedto.EmployeeDTO;
import com.example.Employee.Employeerepository.EmployeeRepository;
import com.example.Employee.employee.EmployeeModel;
 
@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeerepository;
	
	
	
	// to get and update employee   
	
	public EmployeeDTO saveOrUpdateEmployee(EmployeeDTO employeeDTO) {
	    EmployeeModel employeeEntity;

	    // If ID is null or 0, create a new employee
	    if (employeeDTO.getId() == null || employeeDTO.getId() == 0) {
	        employeeEntity = convertToEntity(employeeDTO);
	    } else {
	        // Try to find existing employee
	        EmployeeModel optionalEmployee = employeerepository.findByid(employeeDTO.getId());

	        if (optionalEmployee !=null) {
	            employeeEntity = optionalEmployee;
	            employeeEntity.setName(employeeDTO.getName());
	            employeeEntity.setEmail(employeeDTO.getEmail());
	            employeeEntity.setPosition(employeeDTO.getPosition());
	            employeeEntity.setSalary(employeeDTO.getSalary());
	        } else {
	            System.out.println("Warning: Employee with ID " + employeeDTO.getId() + " not found. Creating new one.");
	            employeeEntity = convertToEntity(employeeDTO);
	        }
	    }

	    EmployeeModel savedEntity = employeerepository.save(employeeEntity);
	    return convertToDTO(savedEntity);
	}
	
	
	// to get employee id 
	
	public EmployeeDTO GetemployeebtId(Long id) {
		
		EmployeeModel optionalEmployee = employeerepository.findByIdAndIsDeleteIsFalse(id);
		 
		return convertToDTO(optionalEmployee);
		
	}
	
	
	// to delete the employe
	
	public String deleteEmployeeBySoftDeleting(Long id) {
 	    EmployeeModel optionalEmployee = employeerepository.findById(id).orElse(null);
	    
 	    if (optionalEmployee != null) {
 	        optionalEmployee.setIsDelete(true);
	        
 	        employeerepository.save(optionalEmployee);
	        
	        return "Employee with employee id: " + optionalEmployee.getId() + " deleted successfully";
	    } else {
 	        return "No employee found with id: " + id;
	    }
	}

	
	
//	 to get all
	public List <EmployeeDTO> getAllEmployee() {
 	    List<EmployeeModel> employees = employeerepository.findByIsDeleteIsFalse();
	    
 	    List<EmployeeDTO> employeeDTOs = employees.stream()
	            .map(this::convertToDTO)  
	            .collect(Collectors.toList());
	    
	    // Return the list of EmployeeDTO
	    return employeeDTOs;
	}

	
	 
	 //ghjk
	 
	 /// convert to entity
	    private EmployeeModel convertToEntity(EmployeeDTO dto) {
	        EmployeeModel entity = new EmployeeModel();
	        entity.setId(dto.getId());  
	        entity.setName(dto.getName());
	        entity.setEmail(dto.getEmail());
	        entity.setPosition(dto.getPosition());
	        entity.setSalary(dto.getSalary());
	        return entity;
	    }
	    
	    
    /// convert to dto  

	    private EmployeeDTO convertToDTO(EmployeeModel entity) {
	        EmployeeDTO dto = new EmployeeDTO();
	        dto.setId(entity.getId());
	        dto.setName(entity.getName());
	        dto.setEmail(entity.getEmail());
	        dto.setPosition(entity.getPosition());
	        dto.setSalary(entity.getSalary());
	        return dto;
	    }


	    public String deleteEmployeePermanently(Long id) {
	        if (employeerepository.existsById(id)) {
	        	employeerepository.deleteById(id);
	            return "Employee hard deleted successfully.";
	        } else {
	            return "Employee not found.";
	        }
	    }

}
