package com.jobfair.helmes.backend.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Response")
public record ResponseDto (

        @Schema(description = "Unique session ID to restore progress", example = "8c64d3fd-697c-4e07-8875-81c57efd6276")
        String sessionId,

        @Schema(description = "Gross salary in €", example = "2500")
        Double grossSalary,

        @Schema(description = "Child's date of birth", example = "2027-06-07")
        LocalDate birthDate,

        @Schema(description = "Total amount of benefit for one year", example = "29999.98")
        Double totalAmount,

        @ArraySchema(schema = @Schema(implementation = MonthlyBenefitDto.class),
                arraySchema = @Schema(description = "Monthly payments"))
        List<MonthlyBenefitDto> monthlyBenefits
) {}