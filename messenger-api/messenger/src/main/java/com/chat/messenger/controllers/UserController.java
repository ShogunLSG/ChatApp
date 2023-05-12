package com.chat.messenger.controllers;

import com.chat.messenger.model.request.LoginRequest;
import com.chat.messenger.model.request.RegisterRequest;
import com.chat.messenger.model.response.DeleteAccountResponse;
import com.chat.messenger.model.response.LoginResponse;
import com.chat.messenger.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Authenticate user credentials")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Valid credentials",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LoginResponse.class)
                    )})
    })
    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("Attempting to sign in");
        LoginResponse response = userService.login(request);
        log.info("Sign in {}", response.isSuccessful() ? "successful" : "failed");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create an account")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account created",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LoginResponse.class)
                    )})
    })
    @PostMapping(path = "/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        log.info("Attempting to register user");
        LoginResponse response = userService.register(request);
        log.info("User registration {}", response.isSuccessful() ? "successful" : "failed");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete an account")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Account deleted",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DeleteAccountResponse.class)
                    )})
    })
    @DeleteMapping(path = "/{username}")
    public ResponseEntity<DeleteAccountResponse> deleteAccount(@PathVariable String username) {
        log.info("Deleting user account");
        DeleteAccountResponse response = userService.deleteAccount(username);
        log.info("Account removal {}", response.isSuccessful() ? "successful" : "failed");
        return ResponseEntity.ok(response);
    }
}