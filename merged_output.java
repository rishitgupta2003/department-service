package com.epam.department_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class DepartmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepartmentServiceApplication.class, args);
	}

}

package com.epam.department_service.client;

import com.epam.department_service.entity.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "EMPLOYEE-SERVICE")
public interface EmployeeClient {
    @GetMapping("employees/department/{code}")
    ResponseEntity<List<Employee>> getByDepartment(@PathVariable("code") String departmentCode);
}

package com.epam.department_service.controller;

import com.epam.department_service.entity.Department;
import com.epam.department_service.entity.DepartmentEmployeeDTO;
import com.epam.department_service.service.DepartmentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Create or update department
    @PostMapping
    public ResponseEntity<Department> createOrUpdateDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.ok(savedDepartment);
    }

    // Get all departments
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    // Get department by id
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentService.getDepartmentById(id);
        return department.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/departmentCode/{departmentCode}")
    public ResponseEntity<Department> getDepartmentByDepartmentCode(@PathVariable String departmentCode) {
        Department byDepartmentCode = departmentService.getByDepartmentCode(departmentCode);
        return Optional.of(byDepartmentCode).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete department by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fullInfo/{code}")
    public ResponseEntity<DepartmentEmployeeDTO> getFullDeptInfo(@PathVariable("code") String departmentCode){
        return ResponseEntity.ok(departmentService.getFullInfo(departmentCode));
    }
}

package com.epam.department_service.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String departmentName;

    @Column
    private String departmentDescription;

    @Column
    private String departmentCode;
}

package com.epam.department_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Getter
public class DepartmentEmployeeDTO {
    private Long id;
    private String departmentName;
    private String departmentDescription;
    private String departmentCode;
    List<Employee> listOfEmployee;

}

package com.epam.department_service.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String departmentCode;
}
package com.epam.department_service.repository;

import com.epam.department_service.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // You can define custom queries here if needed
    Department findByDepartmentCode(String departmentCode);
}

package com.epam.department_service.service;

import com.epam.department_service.client.EmployeeClient;
import com.epam.department_service.entity.Department;
import com.epam.department_service.entity.DepartmentEmployeeDTO;
import com.epam.department_service.entity.Employee;
import com.epam.department_service.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeClient employeeClient;


    // Create or update a department
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getByDepartmentCode(String departmentCode){
        return departmentRepository.findByDepartmentCode(departmentCode);
    }

    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Get department by ID
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    // Delete department by ID
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    //Get All Info about Department
    public DepartmentEmployeeDTO getFullInfo(String departmentCode){
        Department byDepartmentCode = departmentRepository.findByDepartmentCode(departmentCode);
        List<Employee> body = employeeClient.getByDepartment(departmentCode).getBody();

        return DepartmentEmployeeDTO.builder()
                .id(byDepartmentCode.getId())
                .departmentName(byDepartmentCode.getDepartmentName())
                .departmentCode(departmentCode)
                .departmentDescription(byDepartmentCode.getDepartmentDescription())
                .listOfEmployee(body)
                .build();
    }
}

