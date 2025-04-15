package com.epam.department_service.service;

import com.epam.department_service.entity.Department;
import com.epam.department_service.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

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
}
