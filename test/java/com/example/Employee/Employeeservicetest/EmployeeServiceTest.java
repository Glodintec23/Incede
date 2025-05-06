package com.example.Employee.Employeeservicetest;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import com.example.Employee.Employeerepository.EmployeeRepository;
import com.example.Employee.Employeeservice.EmployeeService;
import com.example.Employee.employee.EmployeeModel;
import com.example.Employee.Employeedto.EmployeeDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EmployeeServiceTest {

	@Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    // test to get all employees by id 

    @Test
    public void testGetEmployeeById_ReturnsDTO() {
        // Arrange
        Long employeeId = 1L;
        EmployeeModel mockEmployee = new EmployeeModel();
        mockEmployee.setId(employeeId);
        mockEmployee.setName("Alice");

        when(employeeRepository.findByIdAndIsDeleteIsFalse(employeeId)).thenReturn(mockEmployee);

        // Optionally stub convertToDTO if it's a public/protected method
        // For private methods, you test indirectly via return value

        // Act
        EmployeeDTO result = employeeService.GetemployeebtId(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals("Alice", result.getName()); // adjust based on your DTO fields
        assertEquals(employeeId, result.getId());
    }
    
    //  to create new employee
    
    @Test
    void testSaveOrUpdateEmployee_NewEmployee() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(null);
        dto.setName("John Doe");
        dto.setEmail("john@example.com");
        dto.setPosition("Developer");
        dto.setSalary(50000.0);

        // Mock conversion
        EmployeeModel mockEntity = new EmployeeModel();
        mockEntity.setName(dto.getName());
        mockEntity.setEmail(dto.getEmail());
        mockEntity.setPosition(dto.getPosition());
        mockEntity.setSalary(dto.getSalary());

        EmployeeModel savedEntity = new EmployeeModel();
        savedEntity.setId(1L);
        savedEntity.setName(dto.getName());
        savedEntity.setEmail(dto.getEmail());
        savedEntity.setPosition(dto.getPosition());
        savedEntity.setSalary(dto.getSalary());

        // Stubbing (simulate behavior)
        when(employeeRepository.save(any(EmployeeModel.class))).thenReturn(savedEntity);

        // Act
        EmployeeDTO result = employeeService.saveOrUpdateEmployee(dto);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        verify(employeeRepository, times(1)).save(any(EmployeeModel.class));
    }
    
    
    // to update new employee
    
    @Test
    void testSaveOrUpdateEmployee_UpdateExistingEmployee() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(1L);
        dto.setName("Updated Name");
        dto.setEmail("updated@example.com");
        dto.setPosition("Manager");
        dto.setSalary(70000.0);

        EmployeeModel existingEntity = new EmployeeModel();
        existingEntity.setId(1L);
        existingEntity.setName("Old Name");
        existingEntity.setEmail("old@example.com");
        existingEntity.setPosition("Developer");
        existingEntity.setSalary(50000.0);

        when(employeeRepository.findByid(1L)).thenReturn(existingEntity);
        when(employeeRepository.save(any(EmployeeModel.class))).thenReturn(existingEntity);

        EmployeeDTO result = employeeService.saveOrUpdateEmployee(dto);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        verify(employeeRepository, times(1)).findByid(1L);
        verify(employeeRepository, times(1)).save(existingEntity);
    }

    
    // delete employee
    
    @Test
    void testDeleteEmployeeBySoftDeleting_EmployeeExists() {
        Long employeeId = 1L;
        EmployeeModel employee = new EmployeeModel();
        employee.setId(employeeId);
        employee.setIsDelete(false);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(EmployeeModel.class))).thenReturn(employee);

        String result = employeeService.deleteEmployeeBySoftDeleting(employeeId);

        assertEquals("Employee with employee id: 1 deleted successfully", result);
        assertTrue(employee.getIsDelete());
        verify(employeeRepository).save(employee);
    }

    @Test
    void testDeleteEmployeeBySoftDeleting_EmployeeNotFound() {
        Long employeeId = 2L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        String result = employeeService.deleteEmployeeBySoftDeleting(employeeId);

        assertEquals("No employee found with id: 2", result);
        verify(employeeRepository, never()).save(any());
    }
    
    // to get all employees  
    
    @Test
    void testGetAllEmployee() {
        // Mock employee entities
        EmployeeModel emp1 = new EmployeeModel();
        emp1.setId(1L);
        emp1.setName("John");
        emp1.setIsDelete(false);

        EmployeeModel emp2 = new EmployeeModel();
        emp2.setId(2L);
        emp2.setName("Jane");
        emp2.setIsDelete(false);

        List<EmployeeModel> employeeList = Arrays.asList(emp1, emp2);

        when(employeeRepository.findByIsDeleteIsFalse()).thenReturn(employeeList);

        // You might want to stub convertToDTO if it involves complex logic
        // For this, we can use a real method call (spy) or keep it simple if convertToDTO is internal

        // Call the actual method
        List<EmployeeDTO> result = employeeService.getAllEmployee();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("Jane", result.get(1).getName());

        verify(employeeRepository, times(1)).findByIsDeleteIsFalse();
    }
}
