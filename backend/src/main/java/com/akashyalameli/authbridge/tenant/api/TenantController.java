package com.akashyalameli.authbridge.tenant.api;

import com.akashyalameli.authbridge.tenant.application.TenantService;
import com.akashyalameli.authbridge.tenant.domain.Tenant;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
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
}
