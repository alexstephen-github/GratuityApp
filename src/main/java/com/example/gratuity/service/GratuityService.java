package com.example.gratuity.service;

import com.example.gratuity.dto.GratuityRequest;
import com.example.gratuity.dto.GratuityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class GratuityService {

    private static final Logger logger = LoggerFactory.getLogger(GratuityService.class);

    private static final int DAYS_FACTOR = 15;
    private static final int DIVISOR = 26;
    private static final int ELIGIBILITY_THRESHOLD = 5;
    private static final int ROUNDING_MONTH_THRESHOLD = 6;
    private static final String CURRENCY = "INR";
    private static final String FORMULA = "(basicPlusDa * 15 * serviceYearsConsidered) / 26";

    public GratuityResponse calculate(GratuityRequest request) {
        logger.info("Calculating gratuity for basicPlusDa={}, yearsOfService={}, monthsOfService={}",
                request.getBasicPlusDa(), request.getYearsOfService(), request.getMonthsOfService());

        int yearsOfService = request.getYearsOfService();
        int monthsOfService = request.getMonthsOfService();
        double basicPlusDa = request.getBasicPlusDa();

        // Step 1: Tenure rounding
        int serviceYearsConsidered;
        String roundingReason;

        if (monthsOfService > ROUNDING_MONTH_THRESHOLD) {
            serviceYearsConsidered = yearsOfService + 1;
            roundingReason = String.format("Months of service (%d) > %d, rounded up to next year",
                    monthsOfService, ROUNDING_MONTH_THRESHOLD);
        } else {
            serviceYearsConsidered = yearsOfService;
            roundingReason = String.format("Months of service (%d) <= %d, not rounded",
                    monthsOfService, ROUNDING_MONTH_THRESHOLD);
        }

        // Step 2: Eligibility check
        boolean eligible = serviceYearsConsidered >= ELIGIBILITY_THRESHOLD;
        String eligibilityReason;

        if (eligible) {
            eligibilityReason = String.format("Service years considered (%d) is >= %d",
                    serviceYearsConsidered, ELIGIBILITY_THRESHOLD);
        } else {
            eligibilityReason = String.format("Service years considered (%d) is < %d",
                    serviceYearsConsidered, ELIGIBILITY_THRESHOLD);
        }

        // Step 3: Calculate gratuity amount
        double gratuityAmount = 0.0;
        if (eligible) {
            BigDecimal salary = BigDecimal.valueOf(basicPlusDa);
            BigDecimal amount = salary
                    .multiply(BigDecimal.valueOf(DAYS_FACTOR))
                    .multiply(BigDecimal.valueOf(serviceYearsConsidered))
                    .divide(BigDecimal.valueOf(DIVISOR), 2, RoundingMode.HALF_UP);
            gratuityAmount = amount.doubleValue();
        }

        // Step 4: Build response
        GratuityResponse response = new GratuityResponse();
        response.setEligible(eligible);
        response.setEligibilityReason(eligibilityReason);
        response.setServiceYearsConsidered(serviceYearsConsidered);
        response.setRoundingReason(roundingReason);
        response.setGratuityAmount(gratuityAmount);
        response.setCurrency(CURRENCY);
        response.setFormula(FORMULA);

        Map<String, Object> breakdown = new LinkedHashMap<>();
        breakdown.put("basicPlusDa", basicPlusDa);
        breakdown.put("daysFactor", DAYS_FACTOR);
        breakdown.put("serviceYearsConsidered", serviceYearsConsidered);
        breakdown.put("divisor", DIVISOR);
        response.setCalculationBreakdown(breakdown);

        logger.info("Gratuity calculation result: eligible={}, amount={}", eligible, gratuityAmount);
        return response;
    }
}
