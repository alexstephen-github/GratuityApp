package com.example.gratuity.service;

import com.example.gratuity.dto.GratuityRequest;
import com.example.gratuity.dto.GratuityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GratuityServiceTest {

    private GratuityService gratuityService;

    @BeforeEach
    void setUp() {
        gratuityService = new GratuityService();
    }

    @Test
    @DisplayName("Eligible: 7 years 8 months with 50000 salary - rounds up to 8 years")
    void testEligibleWithRounding() {
        GratuityRequest request = new GratuityRequest(50000.0, 7, 8);
        GratuityResponse response = gratuityService.calculate(request);

        assertTrue(response.isEligible());
        assertEquals(8, response.getServiceYearsConsidered());
        assertEquals(230769.23, response.getGratuityAmount(), 0.01);
        assertEquals("INR", response.getCurrency());
        assertTrue(response.getRoundingReason().contains("rounded up"));
        assertTrue(response.getEligibilityReason().contains(">= 5"));
    }

    @Test
    @DisplayName("Not Eligible: 3 years 2 months with 40000 salary - no rounding, not eligible")
    void testNotEligibleNoRounding() {
        GratuityRequest request = new GratuityRequest(40000.0, 3, 2);
        GratuityResponse response = gratuityService.calculate(request);

        assertFalse(response.isEligible());
        assertEquals(3, response.getServiceYearsConsidered());
        assertEquals(0.0, response.getGratuityAmount());
        assertTrue(response.getRoundingReason().contains("not rounded"));
        assertTrue(response.getEligibilityReason().contains("< 5"));
    }

    @Test
    @DisplayName("Eligible: exactly 5 years 0 months")
    void testExactlyFiveYears() {
        GratuityRequest request = new GratuityRequest(30000.0, 5, 0);
        GratuityResponse response = gratuityService.calculate(request);

        assertTrue(response.isEligible());
        assertEquals(5, response.getServiceYearsConsidered());
        // (30000 * 15 * 5) / 26 = 86538.46
        assertEquals(86538.46, response.getGratuityAmount(), 0.01);
    }

    @Test
    @DisplayName("Not Eligible: 4 years 6 months - months exactly 6, no rounding")
    void testFourYearsSixMonths() {
        GratuityRequest request = new GratuityRequest(50000.0, 4, 6);
        GratuityResponse response = gratuityService.calculate(request);

        assertFalse(response.isEligible());
        assertEquals(4, response.getServiceYearsConsidered());
        assertEquals(0.0, response.getGratuityAmount());
        assertTrue(response.getRoundingReason().contains("not rounded"));
    }

    @Test
    @DisplayName("Eligible: 4 years 7 months - rounds up to 5 years, becomes eligible")
    void testRoundingMakesEligible() {
        GratuityRequest request = new GratuityRequest(60000.0, 4, 7);
        GratuityResponse response = gratuityService.calculate(request);

        assertTrue(response.isEligible());
        assertEquals(5, response.getServiceYearsConsidered());
        // (60000 * 15 * 5) / 26 = 173076.92
        assertEquals(173076.92, response.getGratuityAmount(), 0.01);
    }

    @Test
    @DisplayName("Eligible: 10 years 0 months with high salary")
    void testHighSalaryLongTenure() {
        GratuityRequest request = new GratuityRequest(100000.0, 10, 0);
        GratuityResponse response = gratuityService.calculate(request);

        assertTrue(response.isEligible());
        assertEquals(10, response.getServiceYearsConsidered());
        // (100000 * 15 * 10) / 26 = 576923.08
        assertEquals(576923.08, response.getGratuityAmount(), 0.01);
    }

    @Test
    @DisplayName("Response contains correct formula and breakdown")
    void testResponseContainsFormulaAndBreakdown() {
        GratuityRequest request = new GratuityRequest(50000.0, 7, 8);
        GratuityResponse response = gratuityService.calculate(request);

        assertEquals("(basicPlusDa * 15 * serviceYearsConsidered) / 26", response.getFormula());
        assertNotNull(response.getCalculationBreakdown());
        assertEquals(50000.0, response.getCalculationBreakdown().get("basicPlusDa"));
        assertEquals(15, response.getCalculationBreakdown().get("daysFactor"));
        assertEquals(8, response.getCalculationBreakdown().get("serviceYearsConsidered"));
        assertEquals(26, response.getCalculationBreakdown().get("divisor"));
    }
}
