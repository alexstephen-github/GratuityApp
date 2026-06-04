package com.example.gratuity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request payload for gratuity calculation")
public class GratuityRequest {

    @Schema(description = "Last drawn basic salary plus Dearness Allowance (DA) in INR", example = "50000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "basicPlusDa is required")
    @Positive(message = "basicPlusDa must be greater than 0")
    private Double basicPlusDa;

    @Schema(description = "Completed years of service", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "yearsOfService is required")
    @Min(value = 0, message = "yearsOfService must be >= 0")
    private Integer yearsOfService;

    @Schema(description = "Additional months of service beyond completed years (0–11)", example = "8", requiredMode = Schema.RequiredMode.REQUIRED)
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
