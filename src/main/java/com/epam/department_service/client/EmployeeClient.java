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
