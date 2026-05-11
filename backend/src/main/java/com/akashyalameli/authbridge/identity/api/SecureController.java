package com.akashyalameli.authbridge.identity.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akashyalameli.authbridge.tenant.application.TenantContext;

import java.util.Map;

@RestController
public class SecureController {

    @GetMapping("/api/identity/me")
    public Map<String, Object> me(Authentication authentication) {
        return Map.of(
            "userId", authentication.getName(),
            "roles", authentication.getAuthorities(),
            "tenantId", TenantContext.get()
        );
    }
}
