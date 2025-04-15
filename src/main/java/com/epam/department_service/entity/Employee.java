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