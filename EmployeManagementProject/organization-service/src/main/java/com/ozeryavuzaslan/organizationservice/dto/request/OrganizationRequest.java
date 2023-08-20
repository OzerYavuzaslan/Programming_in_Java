package com.ozeryavuzaslan.organizationservice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.ozeryavuzaslan.organizationservice.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRequest {
    @Size(min = MIN_CHARACTER_SIZE,  max = MAX_CHARACTER_SIZE, message = INVALID_NAME_DEFINITION)
    private String organizationName;

    @Size(min = MIN_CHARACTER_SIZE,  max = MAX_CHARACTER_SIZE, message = INVALID_NAME_DEFINITION)
    private String organizationDescription;

    @Size(min = MIN_ORGANIZATION_CODE_CHARACTER_SIZE, message = INVALID_CODE_DEFINITION)
    private String organizationCode;
}
