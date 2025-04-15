package com.epam.department_service.repository;

import com.epam.department_service.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // You can define custom queries here if needed
    Department findByDepartmentCode(String departmentCode);
}
