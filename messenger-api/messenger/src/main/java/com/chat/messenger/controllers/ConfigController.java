package com.chat.messenger.controllers;

import com.chat.messenger.model.dto.ConfigDto;
import com.chat.messenger.services.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/config")
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @Operation(summary = "Get app configuration")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Configuration retrieved",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConfigDto.class)
                    )})
    })
    @GetMapping(path = "")
    public ResponseEntity<ConfigDto> getConfig() {
        return ResponseEntity.ok(configService.getConfig());
    }
}