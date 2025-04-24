package com.epam.department_service.client;

import com.epam.department_service.entity.OrganisationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ORGANISATION-SERVICE")
public interface OrganisationClient {
    @GetMapping("organisations/code/{code}")
    ResponseEntity<OrganisationDto> getOrganisationByCode(@PathVariable("code") String organisationCode);
}
