package com.ozeryavuzaslan.departmentservice.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.ozeryavuzaslan.departmentservice.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest {
    @Size(min = MIN_CHARACTER_SIZE,  max = MAX_CHARACTER_SIZE, message = INVALID_DEPARTMENT_NAME_DEFINITION)
    private String name;

    @Size(min = MIN_CHARACTER_SIZE, message = INVALID_DESCRIPTION_DEFINITION)
    private String description;

    @Size(min = MIN_DEPARTMENT_CODE_CHARACTER_SIZE, message = INVALID_CODE_DEFINITION)
    private String code;
}
