package com.ozeryavuzaslan.employeeservice.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.ozeryavuzaslan.employeeservice.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
    @Size(min = MIN_CHARACTER_SIZE,  max = MAX_CHARACTER_SIZE, message = INVALID_NAME_SURNAME_DEFINITION)
    private String name;

    @Size(min = MIN_CHARACTER_SIZE,  max = MAX_CHARACTER_SIZE, message = INVALID_NAME_SURNAME_DEFINITION)
    private String surname;

    @Email(message = INVALID_EMAIL_DEFINITION)
    @NotNull(message = INVALID_EMAIL_DEFINITION)
    @NotBlank(message = INVALID_EMAIL_DEFINITION)
    private String email;

    @Size(min = MIN_DEPARTMENT_CODE_CHARACTER_SIZE, message = INVALID_CODE_DEFINITION)
    private String departmentCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = INVALID_BIRTHDATE_DEFINITION)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @PastOrPresent(message = INVALID_START_DATE_DEFINITION)
    private LocalDate startDate;
}