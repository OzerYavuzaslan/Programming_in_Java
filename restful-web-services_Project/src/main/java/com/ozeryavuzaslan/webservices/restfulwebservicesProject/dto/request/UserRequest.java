package com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request;

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

import static com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Size(min = MIN_CHARACTER_SIZE,  max = MAX_CHARACTER_SIZE, message = INVALID_NAME_SURNAME_DEFINITION)
    private String name;

    @Size(min = MIN_CHARACTER_SIZE,  max = MAX_CHARACTER_SIZE, message = INVALID_NAME_SURNAME_DEFINITION)
    private String surname;

    @Email(message = INVALID_EMAIL_DEFINITION)
    @NotNull(message = INVALID_EMAIL_DEFINITION)
    @NotBlank(message = INVALID_EMAIL_DEFINITION)
    private String email;

    @Past(message = INVALID_BIRTHDATE_DEFINITION)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;
}