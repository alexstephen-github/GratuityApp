package com.example.gratuity.dto;

import java.util.Map;

public class GratuityResponse {

    private boolean eligible;
    private String eligibilityReason;
    private int serviceYearsConsidered;
    private String roundingReason;
    private double gratuityAmount;
    private String currency;
    private String formula;
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
