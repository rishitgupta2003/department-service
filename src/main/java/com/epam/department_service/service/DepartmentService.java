package com.epam.department_service.service;

import com.epam.department_service.client.EmployeeClient;
import com.epam.department_service.client.OrganisationClient;
import com.epam.department_service.entity.Department;
import com.epam.department_service.entity.DepartmentEmployeeDTO;
import com.epam.department_service.entity.Employee;
import com.epam.department_service.entity.OrganisationDto;
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
    private final OrganisationClient organisationClient;


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
        OrganisationDto body1 = organisationClient.getOrganisationByCode(byDepartmentCode.getOrgCode()).getBody();

        return DepartmentEmployeeDTO.builder()
                .id(byDepartmentCode.getId())
                .departmentName(byDepartmentCode.getDepartmentName())
                .departmentCode(departmentCode)
                .departmentDescription(byDepartmentCode.getDepartmentDescription())
                .listOfEmployee(body)
                .organisationDto(body1)
                .build();
    }
}
