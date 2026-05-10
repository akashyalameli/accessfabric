package com.akashyalameli.authbridge.auth.api;

import com.akashyalameli.authbridge.auth.application.AuthService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @SecurityRequirements()
    public Map<String, String> register(
            @RequestParam UUID tenantId,
            @RequestParam String email,
            @RequestParam String password
    ) {
        service.register(tenantId, email, password);
        return Map.of("status", "registered");
    }

    @PostMapping("/login")
    @SecurityRequirements()
    public TokenResponse login(
            @RequestParam UUID tenantId,
            @RequestParam String email,
            @RequestParam String password
    ) {
        return service.login(tenantId, email, password);
    }

    @PostMapping("/refresh")
    @SecurityRequirements()
    public TokenResponse refresh(
            @RequestParam String refreshToken
    ) {
        return service.refresh(refreshToken);
    }
}
