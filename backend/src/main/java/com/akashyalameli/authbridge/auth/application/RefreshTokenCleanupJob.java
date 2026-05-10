package com.akashyalameli.authbridge.auth.application;

import com.akashyalameli.authbridge.auth.infrastructure.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RefreshTokenCleanupJob {

    private final RefreshTokenRepository repository;

    public RefreshTokenCleanupJob(
            RefreshTokenRepository repository
    ) {
        this.repository = repository;
    }

    @Scheduled(cron = "0 0 0 * * *") // Run daily at midnight
    public void cleanup() {
        repository.deleteByRevokedTrueOrExpiresAtBefore(Instant.now());
    }
}
