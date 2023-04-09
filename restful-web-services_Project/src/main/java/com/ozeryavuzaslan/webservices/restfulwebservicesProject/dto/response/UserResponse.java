package com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "UserResponse Model Information"
)
public class UserResponse {
    @Schema(
            description = "User name"
    )
    private String name;
    private String surname;
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}