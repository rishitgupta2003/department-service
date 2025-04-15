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
