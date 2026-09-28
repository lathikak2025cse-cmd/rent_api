package com.example.rent_api.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.rent_api.Model.Tenant;
import com.example.rent_api.Service.TenantService;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public Tenant addTenant(@RequestBody Tenant tenant) {

        return tenantService.addTenant(tenant);
    }

    @GetMapping
    public List<Tenant> getAllTenants() {

        return tenantService.getAllTenants();
    }

    @GetMapping("/{id}")
    public Tenant getTenantById(@PathVariable Long id) {

        return tenantService.getTenantById(id);
    }
}