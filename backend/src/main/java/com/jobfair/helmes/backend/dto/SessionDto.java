package com.jobfair.helmes.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Session ID")
public record SessionDto(
        @Schema(description = "Unique session ID to restore progress", example = "8c64d3fd-697c-4e07-8875-81c57efd6276")
        String sessionId
) {}
