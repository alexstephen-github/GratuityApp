package com.example.gratuity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GratuityRequest {

    @NotNull(message = "basicPlusDa is required")
    @Positive(message = "basicPlusDa must be greater than 0")
    private Double basicPlusDa;

    @NotNull(message = "yearsOfService is required")
    @Min(value = 0, message = "yearsOfService must be >= 0")
    private Integer yearsOfService;

    @NotNull(message = "monthsOfService is required")
    @Min(value = 0, message = "monthsOfService must be between 0 and 11")
    @Max(value = 11, message = "monthsOfService must be between 0 and 11")
    private Integer monthsOfService;

    public GratuityRequest() {
    }

    public GratuityRequest(Double basicPlusDa, Integer yearsOfService, Integer monthsOfService) {
        this.basicPlusDa = basicPlusDa;
        this.yearsOfService = yearsOfService;
        this.monthsOfService = monthsOfService;
    }

    public Double getBasicPlusDa() {
        return basicPlusDa;
    }

    public void setBasicPlusDa(Double basicPlusDa) {
        this.basicPlusDa = basicPlusDa;
    }

    public Integer getYearsOfService() {
        return yearsOfService;
    }

    public void setYearsOfService(Integer yearsOfService) {
        this.yearsOfService = yearsOfService;
    }

    public Integer getMonthsOfService() {
        return monthsOfService;
    }

    public void setMonthsOfService(Integer monthsOfService) {
        this.monthsOfService = monthsOfService;
    }
}
