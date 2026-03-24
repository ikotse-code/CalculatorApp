package com.jobfair.helmes.backend.job;

import com.jobfair.helmes.backend.entity.Calculator;
import com.jobfair.helmes.backend.repository.CalculatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpiredSessionCleaner {

    private final CalculatorRepository repository;

    @Scheduled(cron = "0 0 0 * * ?") // midnight
    public void deleteExpiredSessions() {
        log.info("Running session cleanup job...");

        LocalDateTime now = LocalDateTime.now();

        List<Calculator> expired = repository.findAllByExpiresAtBefore(now);

        if (expired.isEmpty()) {
            log.info("No expired sessions found");
            return;
        }

        repository.deleteAll(expired);
        log.info("Deleted {} expired sessions", expired.size());
    }

    public void runNow() {
        deleteExpiredSessions();
    }
}
