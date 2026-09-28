package com.example.rent_api.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.rent_api.Model.Tenant;
import com.example.rent_api.Repository.TenantRepository;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Tenant addTenant(Tenant tenant) {

        return tenantRepository.save(tenant);
    }

    @Override
    public List<Tenant> getAllTenants() {

        return tenantRepository.findAll();
    }

    @Override
    public Tenant getTenantById(Long id) {

        return tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
    }
}