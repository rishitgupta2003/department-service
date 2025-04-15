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
