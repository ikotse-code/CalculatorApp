package com.jobfair.helmes.backend.repository;

import com.jobfair.helmes.backend.entity.Calculator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CalculatorRepository extends JpaRepository<Calculator, Long> {

    boolean existsBySessionId(String sessionId);

    Optional<Calculator> findBySessionId(String sessionId);

    List<Calculator> findAllByExpiresAtBefore(LocalDateTime time);
}