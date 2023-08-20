package com.ozeryavuzaslan.organizationservice.service;

import com.ozeryavuzaslan.organizationservice.dto.request.OrganizationRequest;
import com.ozeryavuzaslan.organizationservice.dto.response.OrganizationResponse;

import java.util.List;

public interface OrganizationService {
    OrganizationResponse save(OrganizationRequest organizationRequest);
    OrganizationResponse fineOne(String code);
    List<OrganizationResponse> findAll();
}
