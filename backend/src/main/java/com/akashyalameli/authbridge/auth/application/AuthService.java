package com.akashyalameli.authbridge.auth.application;

import com.akashyalameli.authbridge.auth.api.TokenResponse;
import com.akashyalameli.authbridge.auth.domain.RefreshToken;
import com.akashyalameli.authbridge.auth.infrastructure.RefreshTokenRepository;
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
                Instant.now()
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
        
        String accessToken = jwtService.generate(user.getId().toString(), user.getTenantId().toString());
        String refreshTokenValue = generateRefreshToken();

        RefreshToken refreshToken = new RefreshToken(
            UUID.randomUUID(),
            user.getId(),
            user.getTenantId(),
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

        String accessToken =
                jwtService.generate(existing.getUserId().toString(), existing.getTenantId().toString());

        String newRefresh = generateRefreshToken();

        RefreshToken replacement = new RefreshToken(
                UUID.randomUUID(),
                existing.getUserId(),
                existing.getTenantId(),
                newRefresh,
                Instant.now().plusSeconds(604800),
                false,
                Instant.now()
        );

        refreshTokens.save(replacement);

        return new TokenResponse(accessToken, newRefresh);
    }
}
