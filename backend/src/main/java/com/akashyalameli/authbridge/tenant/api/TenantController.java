package com.akashyalameli.authbridge.tenant.api;

import com.akashyalameli.authbridge.shared.support.StringSupport;
import com.akashyalameli.authbridge.tenant.application.TenantContext;
import com.akashyalameli.authbridge.tenant.application.TenantService;
import com.akashyalameli.authbridge.tenant.domain.Tenant;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    private final TenantService service;

    public TenantController(TenantService service) {
        this.service = service;
    }

    @PostMapping
    public Tenant create(@RequestBody CreateTenantRequest request) {
        return service.create(request.name(), request.slug());
    }

    @GetMapping
    public List<Tenant> list() {
        return service.list();
    }

    @GetMapping("/context")
    public Map<String, String> context() {
        return Map.of(
                "tenantId",
                StringSupport.isNotNullAndNotEmpty(TenantContext.get()) ? TenantContext.get() : "No tenant context set"
        );
    }
}
