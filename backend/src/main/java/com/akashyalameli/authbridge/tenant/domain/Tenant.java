package com.akashyalameli.authbridge.tenant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tenants")
@NoArgsConstructor @AllArgsConstructor
public class Tenant {

    @Id @Getter
    private UUID id;

    @Column(nullable = false) @Getter
    private String name;

    @Column(nullable = false, unique = true) @Getter
    private String slug;

    @Column(name = "created_at", nullable = false) @Getter
    private Instant createdAt;
}
