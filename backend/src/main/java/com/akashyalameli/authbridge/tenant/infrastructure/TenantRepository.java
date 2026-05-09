package com.akashyalameli.authbridge.tenant.infrastructure;

import com.akashyalameli.authbridge.tenant.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
}
