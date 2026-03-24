package com.jobfair.helmes.backend.controller;

import com.jobfair.helmes.backend.dto.RequestDto;
import com.jobfair.helmes.backend.dto.ResponseDto;
import com.jobfair.helmes.backend.dto.SessionDto;
import com.jobfair.helmes.backend.service.CalculatorService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
@RequiredArgsConstructor
@Tag(name = "Calculator", description = "API for calculating parental benefits")
public class CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping("/session")
    @ApiResponse(responseCode = "200", description = "OK: Created successfully")
    public ResponseEntity<SessionDto> createSession() {
        String sessionId = calculatorService.createNewSession();
        return ResponseEntity.ok(new SessionDto(sessionId));
    }

    @PostMapping("/calculate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Calculated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid request data", content = @Content(schema = @Schema(type = "string")))
    })
    public ResponseDto calculate(@Valid @RequestBody RequestDto request) {
        return calculatorService.calculate(request);
    }

    @GetMapping("/restore/{sessionId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Restored successfully"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Session not found", content = @Content(schema = @Schema(type = "string")))
    })
    public ResponseDto restore(@PathVariable String sessionId) {
        return calculatorService.restoreBySessionId(sessionId);
    }
}
