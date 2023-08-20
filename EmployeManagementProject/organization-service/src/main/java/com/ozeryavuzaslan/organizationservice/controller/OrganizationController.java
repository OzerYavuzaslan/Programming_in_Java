package com.ozeryavuzaslan.organizationservice.controller;

import com.ozeryavuzaslan.organizationservice.dto.request.OrganizationRequest;
import com.ozeryavuzaslan.organizationservice.dto.response.OrganizationResponse;
import com.ozeryavuzaslan.organizationservice.service.OrganizationService;
import com.ozeryavuzaslan.organizationservice.util.CustomLocation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ozeryavuzaslan.organizationservice.util.Constants.ORGANIZATION_CODE_ENDPOINT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;
    private final CustomLocation customLocation;

    @PostMapping
    public ResponseEntity<OrganizationResponse> createOrganization(@Valid @RequestBody OrganizationRequest organizationRequest){
        return ResponseEntity
                .created(customLocation
                        .getURILocation(ORGANIZATION_CODE_ENDPOINT,
                                organizationService
                                        .save(organizationRequest)
                                        .getOrganizationCode()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<OrganizationResponse>> retrieveAllOrganizations(){
        return ResponseEntity.ok(organizationService.findAll());
    }

    @GetMapping("/getByOrganizationCode/{code}")
    public ResponseEntity<OrganizationResponse> retrieveOrganizationByCode(@PathVariable String code){
        return ResponseEntity.ok(organizationService.fineOne(code));
    }
}