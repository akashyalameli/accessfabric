package com.akashyalameli.authbridge.auth.application;

import com.akashyalameli.authbridge.auth.api.TokenResponse;
import com.akashyalameli.authbridge.auth.domain.RefreshToken;
import com.akashyalameli.authbridge.auth.infrastructure.RefreshTokenRepository;
import com.akashyalameli.authbridge.identity.domain.Role;
import com.akashyalameli.authbridge.identity.domain.User;
import com.akashyalameli.authbridge.identity.infrastructure.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokens;

    private final UserRepository users;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwtService, RefreshTokenRepository refreshTokens) {
        this.users = users;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.refreshTokens = refreshTokens;
    }

    public User register(UUID tenantId, String email, String password) {
        User user = new User(
                UUID.randomUUID(),
                tenantId,
                email,
                encoder.encode(password),
                Instant.now(),
                Role.USER
        );

        return users.save(user);
    }

    public User registerAdmin(UUID tenantId, String email, String password) {
        User user = new User(
                UUID.randomUUID(),
                tenantId,
                email,
                encoder.encode(password),
                Instant.now(),
                Role.ADMIN
        );

        return users.save(user);
    }

    private String generateRefreshToken() {
        return UUID.randomUUID() + "-" + UUID.randomUUID();
    }

    public TokenResponse login(UUID tenantId, String email, String password) {
        User user = users.findByTenantIdAndEmail(tenantId, email)
                .filter(u -> encoder.matches(password, u.getPasswordHash()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        
        String accessToken = jwtService.generate(user.getId().toString(), user.getTenantId().toString(), user.getRole());
        String refreshTokenValue = generateRefreshToken();

        RefreshToken refreshToken = new RefreshToken(
            UUID.randomUUID(),
            user.getId(),
            refreshTokenValue,
            Instant.now().plusSeconds(604800),
            false,
            Instant.now()
        );

        refreshTokens.save(refreshToken);

        return new TokenResponse(accessToken, refreshTokenValue);
    }

    public TokenResponse refresh(String token) {
        RefreshToken existing = refreshTokens.findByToken(token)
                .filter(rt -> !rt.isRevoked())
                .filter(rt -> rt.getExpiresAt().isAfter(Instant.now()))
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        existing.revoke();
        refreshTokens.save(existing);

        User user = users.findById(existing.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken =
                jwtService.generate(user.getId().toString(), user.getTenantId().toString(), user.getRole());

        String newRefresh = generateRefreshToken();

        RefreshToken replacement = new RefreshToken(
                UUID.randomUUID(),
                user.getId(),
                newRefresh,
                Instant.now().plusSeconds(604800),
                false,
                Instant.now()
        );

        refreshTokens.save(replacement);

        return new TokenResponse(accessToken, newRefresh);
    }
}
