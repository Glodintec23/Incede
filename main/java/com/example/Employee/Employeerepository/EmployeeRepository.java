package com.example.Employee.Employeerepository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.Employee.employee.EmployeeModel;

public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long>{
	
    @Query("SELECT e FROM EmployeeModel e WHERE e.id = :id AND e.isDelete ='false'")
    EmployeeModel findByIdAndIsDeleteIsFalse(@Param("id") Long id);
 

	EmployeeModel findByid(Long id);


	@Query("SELECT e FROM EmployeeModel e WHERE e.isDelete = 'false'")
	List<EmployeeModel> findByIsDeleteIsFalse();



}
