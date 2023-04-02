package com.ozeryavuzaslan.webservices.restfulwebservicesProject.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.ozeryavuzaslan.webservices.restfulwebservicesProject.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    @Size(min = MIN_CHARACTER_SIZE,  max = MAX_CHARACTER_SIZE, message = TITLE_DEFINITION)
    private String title;

    @NotNull(message = POST_DEFINITION)
    @NotEmpty(message = POST_DEFINITION)
    private String post;
}
