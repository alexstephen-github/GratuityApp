package com.example.gratuity.controller;

import com.example.gratuity.dto.GratuityRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GratuityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/v1/gratuity/calculate - eligible scenario")
    void testCalculateEligible() throws Exception {
        GratuityRequest request = new GratuityRequest(50000.0, 7, 8);

        mockMvc.perform(post("/api/v1/gratuity/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eligible").value(true))
                .andExpect(jsonPath("$.serviceYearsConsidered").value(8))
                .andExpect(jsonPath("$.gratuityAmount").value(230769.23))
                .andExpect(jsonPath("$.currency").value("INR"));
    }

    @Test
    @DisplayName("POST /api/v1/gratuity/calculate - not eligible scenario")
    void testCalculateNotEligible() throws Exception {
        GratuityRequest request = new GratuityRequest(40000.0, 3, 2);

        mockMvc.perform(post("/api/v1/gratuity/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eligible").value(false))
                .andExpect(jsonPath("$.serviceYearsConsidered").value(3))
                .andExpect(jsonPath("$.gratuityAmount").value(0.0));
    }

    @Test
    @DisplayName("POST /api/v1/gratuity/calculate - validation error for negative salary")
    void testValidationErrorNegativeSalary() throws Exception {
        String invalidJson = "{\"basicPlusDa\": -1000, \"yearsOfService\": 5, \"monthsOfService\": 3}";

        mockMvc.perform(post("/api/v1/gratuity/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    @DisplayName("POST /api/v1/gratuity/calculate - validation error for months > 11")
    void testValidationErrorMonthsExceed() throws Exception {
        String invalidJson = "{\"basicPlusDa\": 50000, \"yearsOfService\": 5, \"monthsOfService\": 15}";

        mockMvc.perform(post("/api/v1/gratuity/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    @DisplayName("POST /api/v1/gratuity/calculate - validation error for missing fields")
    void testValidationErrorMissingFields() throws Exception {
        String invalidJson = "{}";

        mockMvc.perform(post("/api/v1/gratuity/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors").isArray());
    }
}
