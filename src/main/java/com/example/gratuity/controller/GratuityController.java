package com.example.gratuity.controller;

import com.example.gratuity.dto.GratuityRequest;
import com.example.gratuity.dto.GratuityResponse;
import com.example.gratuity.service.GratuityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gratuity")
public class GratuityController {

    private final GratuityService gratuityService;

    public GratuityController(GratuityService gratuityService) {
        this.gratuityService = gratuityService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<GratuityResponse> calculateGratuity(@Valid @RequestBody GratuityRequest request) {
        GratuityResponse response = gratuityService.calculate(request);
        return ResponseEntity.ok(response);
    }
}
