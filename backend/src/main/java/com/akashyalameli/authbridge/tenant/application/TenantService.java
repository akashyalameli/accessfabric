package com.akashyalameli.authbridge.tenant.application;

import com.akashyalameli.authbridge.tenant.domain.Tenant;
import com.akashyalameli.authbridge.tenant.infrastructure.TenantRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TenantService {

    private final TenantRepository repository;

    public TenantService(TenantRepository repository) {
        this.repository = repository;
    }

    public Tenant create(String name, String slug) {
        Tenant tenant = new Tenant(
                UUID.randomUUID(),
                name,
                slug,
                Instant.now()
        );

        return repository.save(tenant);
    }

    public List<Tenant> list() {
        return repository.findAll();
    }
}
