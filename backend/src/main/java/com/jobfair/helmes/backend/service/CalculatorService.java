package com.jobfair.helmes.backend.service;

import com.jobfair.helmes.backend.domain.CalculatorDomain;
import com.jobfair.helmes.backend.dto.MonthlyBenefitDto;
import com.jobfair.helmes.backend.dto.RequestDto;
import com.jobfair.helmes.backend.dto.ResponseDto;
import com.jobfair.helmes.backend.entity.Calculator;
import com.jobfair.helmes.backend.exception.ResourceNotFoundException;
import com.jobfair.helmes.backend.exception.SessionSaveException;
import com.jobfair.helmes.backend.repository.CalculatorRepository;
import com.jobfair.helmes.backend.util.MathUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculatorService {

    private final CalculatorRepository repository;
    private final CalculatorDomain calculator;

    @Value("${app.session.ttl-days}")
    private long ttlDays;


    public String createNewSession() {
        String sessionId;
        do {
            sessionId = UUID.randomUUID().toString();
        } while (repository.existsBySessionId(sessionId));

        log.info("Created new sessionId: {}", sessionId);
        return sessionId;
    }

    public ResponseDto calculate(RequestDto request) {

        validate(request);

        List<MonthlyBenefitDto> monthlyBenefits =
                calculator.calculate(request.getGrossSalary(), request.getBirthDate());

        saveSession(request.getSessionId(), request);

        return buildResponse(request.getSessionId(), request.getGrossSalary(),
                request.getBirthDate(), monthlyBenefits);
    }

    public ResponseDto restoreBySessionId(String sessionId) {

        log.info("Attempting to restore session with ID: {}", sessionId);

        Calculator entity = repository.findBySessionId(sessionId)
                .orElseThrow(() -> {
                    log.error("Session not found with ID: {}", sessionId);
                    return new ResourceNotFoundException("Session not found with ID: " + sessionId);
                });
        if (entity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResourceNotFoundException("Session expired");
        }

        List<MonthlyBenefitDto> monthlyBenefits =
                calculator.calculate(entity.getGrossSalary(), entity.getBirthDate());

        entity.touch();
        entity.refreshExpiration(ttlDays);
        repository.save(entity);

        log.info("Successfully restored session with ID: {}", sessionId);
        return buildResponse(entity.getSessionId(), entity.getGrossSalary(),
                entity.getBirthDate(), monthlyBenefits);
    }

    private ResponseDto buildResponse(String sessionId, Double grossSalary, LocalDate birthDate,
                                      List<MonthlyBenefitDto> monthlyBenefits) {

        double total = monthlyBenefits.stream()
                .mapToDouble(MonthlyBenefitDto::amount)
                .sum();

        total = MathUtils.round2(total);

        return new ResponseDto(sessionId, grossSalary, birthDate, total, monthlyBenefits);
    }

    @Transactional
    private void saveSession(String sessionId, RequestDto request) {

        Calculator entity = repository.findBySessionId(sessionId)
                .orElseGet(() -> {
                    Calculator c = Calculator.create(sessionId,
                            request.getGrossSalary(),
                            request.getBirthDate());

                    c.initializeExpiration(ttlDays);
                    return c;
                });

        if (entity.getId() != null) {
            if (entity.getExpiresAt().isBefore(LocalDateTime.now())) {
                throw new ResourceNotFoundException("Session expired");
            } else {

            entity.updateData(request.getGrossSalary(), request.getBirthDate());
            entity.refreshExpiration(ttlDays);
            }
        }

        try {
            repository.save(entity);
            log.info("Successfully saved session with ID: {}", sessionId);

        } catch (Exception ex) {
            log.error("Failed to save session with ID: {}", sessionId, ex);
            throw new SessionSaveException("Failed to save session with ID: " + sessionId, ex);
        }
    }

    private void validate(RequestDto request) {
        if (request.getSessionId() == null || request.getSessionId().isBlank()) {
            throw new IllegalArgumentException("SessionId required");
        }
    }
}