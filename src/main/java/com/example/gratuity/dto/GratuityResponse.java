package com.example.gratuity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(description = "Gratuity calculation result")
public class GratuityResponse {

    @Schema(description = "Whether the employee is eligible for gratuity", example = "true")
    private boolean eligible;

    @Schema(description = "Reason for eligibility or ineligibility", example = "Eligible: service >= 5 years")
    private String eligibilityReason;

    @Schema(description = "Rounded service years used in the calculation", example = "10")
    private int serviceYearsConsidered;

    @Schema(description = "Explanation of any rounding applied to service duration", example = "Months >= 6, rounded up to 11 years")
    private String roundingReason;

    @Schema(description = "Calculated gratuity amount in INR", example = "288461.54")
    private double gratuityAmount;

    @Schema(description = "Currency of the gratuity amount", example = "INR")
    private String currency;

    @Schema(description = "Formula used for calculation", example = "(Basic+DA) × 15/26 × Years")
    private String formula;

    @Schema(description = "Step-by-step breakdown of the calculation")
    private Map<String, Object> calculationBreakdown;


    public GratuityResponse() {
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public String getEligibilityReason() {
        return eligibilityReason;
    }

    public void setEligibilityReason(String eligibilityReason) {
        this.eligibilityReason = eligibilityReason;
    }

    public int getServiceYearsConsidered() {
        return serviceYearsConsidered;
    }

    public void setServiceYearsConsidered(int serviceYearsConsidered) {
        this.serviceYearsConsidered = serviceYearsConsidered;
    }

    public String getRoundingReason() {
        return roundingReason;
    }

    public void setRoundingReason(String roundingReason) {
        this.roundingReason = roundingReason;
    }

    public double getGratuityAmount() {
        return gratuityAmount;
    }

    public void setGratuityAmount(double gratuityAmount) {
        this.gratuityAmount = gratuityAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Map<String, Object> getCalculationBreakdown() {
        return calculationBreakdown;
    }

    public void setCalculationBreakdown(Map<String, Object> calculationBreakdown) {
        this.calculationBreakdown = calculationBreakdown;
    }
}
