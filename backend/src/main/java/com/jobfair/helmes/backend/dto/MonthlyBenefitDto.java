package com.jobfair.helmes.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Monthly payments")
public record MonthlyBenefitDto(
        @Schema(description = "Month name", example = "JUNE")
        String month,

        @Schema(description = "Number of paid days in the month", example = "24")
        int daysPaid,

        @Schema(description = "Amount paid for the month", example = "2000")
        double amount
) {}
