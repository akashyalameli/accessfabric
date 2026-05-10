package com.akashyalameli.authbridge.auth.api;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {}
