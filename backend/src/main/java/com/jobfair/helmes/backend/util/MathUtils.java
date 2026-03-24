package com.jobfair.helmes.backend.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    public static double round2(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
