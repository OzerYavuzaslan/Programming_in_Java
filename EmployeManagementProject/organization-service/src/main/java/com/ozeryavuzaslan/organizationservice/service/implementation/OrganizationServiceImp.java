package com.ozeryavuzaslan.organizationservice.service.implementation;

import com.ozeryavuzaslan.organizationservice.dto.request.OrganizationRequest;
import com.ozeryavuzaslan.organizationservice.dto.response.OrganizationResponse;
import com.ozeryavuzaslan.organizationservice.exception.OrganizationNotFoundException;
import com.ozeryavuzaslan.organizationservice.model.Organization;
import com.ozeryavuzaslan.organizationservice.repository.OrganizationRepository;
import com.ozeryavuzaslan.organizationservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ozeryavuzaslan.organizationservice.util.Constants.ORGANIZATION_NOT_FOUND_DEFINITION;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImp implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrganizationResponse save(OrganizationRequest organizationRequest) {
        return modelMapper
                .map(organizationRepository
                        .save(modelMapper
                                .map(organizationRequest, Organization.class)),
                        OrganizationResponse.class);
    }

    @Override
    public OrganizationResponse fineOne(String code) {
        return modelMapper.map(organizationRepository
                .findByOrganizationCode(code)
                .orElseThrow(
                () -> new OrganizationNotFoundException(ORGANIZATION_NOT_FOUND_DEFINITION)),
                OrganizationResponse.class);
    }

    @Override
    public List<OrganizationResponse> findAll() {
        return organizationRepository
                .findAll()
                .stream()
                .map(organization -> modelMapper.map(organization, OrganizationResponse.class))
                .toList();
    }
}
