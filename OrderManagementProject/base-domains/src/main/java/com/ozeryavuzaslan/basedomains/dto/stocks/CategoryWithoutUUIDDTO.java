package com.ozeryavuzaslan.basedomains.dto.stocks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.ozeryavuzaslan.basedomains.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWithoutUUIDDTO implements Serializable {
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private UUID categoryCode;

    @NotNull(message = CATEGORY_NAME_NULL_MSG)
    @NotBlank(message = CATEGORY_NAME_BLANK_MSG)
    @NotEmpty(message = CATEGORY_NAME_EMPTY_MSG)
    @Size(min = NAME_MIN_SIZE, max = NAME_MAX_SIZE, message = CATEGORY_NAME_SIZE_MSG)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
}
