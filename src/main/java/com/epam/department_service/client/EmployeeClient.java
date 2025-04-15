package com.epam.department_service.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("EMPLOYEE-SERVICE")
public interface EmployeeClient {

}
