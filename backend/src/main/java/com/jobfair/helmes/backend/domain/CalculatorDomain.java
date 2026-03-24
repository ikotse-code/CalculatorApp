package com.jobfair.helmes.backend.domain;

import com.jobfair.helmes.backend.dto.MonthlyBenefitDto;
import com.jobfair.helmes.backend.util.MathUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CalculatorDomain {

    private static final double MAX_MONTHLY_BENEFIT = 4000.0;
    private static final double MIN_MONTHLY_BENEFIT = 886.0;
    private static final int DAYS_FOR_RATE = 30;
    private static final int MONTHS_TO_CALCULATE = 12;

    public List<MonthlyBenefitDto> calculate(Double salary, LocalDate birthDate) {

        salary = Optional.ofNullable(salary).orElse(0.0);

        double dailyRate = salary / DAYS_FOR_RATE;
        double dailyRateMax = MAX_MONTHLY_BENEFIT / DAYS_FOR_RATE;
        double dailyRateMin = MIN_MONTHLY_BENEFIT / DAYS_FOR_RATE;

        List<MonthlyBenefitDto> monthlyBenefits = new ArrayList<>();

        for (int i = 0; i < MONTHS_TO_CALCULATE; i++) {

            LocalDate start = birthDate.plusMonths(i);
            LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

            int days = (i == 0)
                    ? (int) ChronoUnit.DAYS.between(birthDate, end) + 1
                    : end.getDayOfMonth();

            double amount = dailyRate * days;

            if (salary > MAX_MONTHLY_BENEFIT) {
                amount = dailyRateMax * days;
            }

            if (salary < MIN_MONTHLY_BENEFIT) {
                amount = dailyRateMin * days;
            }

            amount = MathUtils.round2(amount);

            monthlyBenefits.add(new MonthlyBenefitDto(
                    start.getMonth().toString(),
                    days,
                    amount
            ));
        }

        return monthlyBenefits;
    }
}
