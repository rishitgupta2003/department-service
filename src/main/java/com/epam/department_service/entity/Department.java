package com.epam.department_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
public class Department {

    @Id
    private Long id;

    @Column
    private String departmentName;

    @Column
    private String departmentDescription;

    @Column
    private String departmentCode;
}
