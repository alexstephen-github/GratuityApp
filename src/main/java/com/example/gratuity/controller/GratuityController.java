package com.example.gratuity.controller;

import com.example.gratuity.dto.ErrorResponse;
import com.example.gratuity.dto.GratuityRequest;
import com.example.gratuity.dto.GratuityResponse;
import com.example.gratuity.service.GratuityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gratuity")
@Tag(name = "Gratuity", description = "Endpoints for gratuity calculation as per the Payment of Gratuity Act, India")
public class GratuityController {

    private final GratuityService gratuityService;

    public GratuityController(GratuityService gratuityService) {
        this.gratuityService = gratuityService;
    }

    @Operation(
            summary = "Calculate gratuity",
            description = "Calculates the gratuity amount for an employee based on last drawn basic + DA and total years/months of service."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gratuity calculated successfully",
                    content = @Content(schema = @Schema(implementation = GratuityResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/calculate")
    public ResponseEntity<GratuityResponse> calculateGratuity(@Valid @RequestBody GratuityRequest request) {
        GratuityResponse response = gratuityService.calculate(request);
        return ResponseEntity.ok(response);
    }
}

