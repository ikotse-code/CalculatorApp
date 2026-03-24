package com.jobfair.helmes.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request")
public class RequestDto {

    @Schema(description = "Unique session ID to restore progress", example = "8c64d3fd-697c-4e07-8875-81c57efd6276")
    @Pattern(regexp = "^[0-9a-fA-F\\-]{36}$", message = "Invalid sessionId format")
    private String sessionId;

    @NotNull (message = "Salary is required")
    @PositiveOrZero(message = "Salary must be positive or zero")
    @Schema(description = "Gross salary in €", example = "2500")
    private Double grossSalary;

    @NotNull(message = "Birth date is required")
    @FutureOrPresent(message = "Birth date must be today or in the future")
    @Schema(description = "Child's date of birth", example = "2027-06-07")
    private LocalDate birthDate;
}
