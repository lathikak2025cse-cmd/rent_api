package com.example.rent_api.Service;

import java.util.List;

import com.example.rent_api.Model.Tenant;

public interface TenantService {

    Tenant addTenant(Tenant tenant);

    List<Tenant> getAllTenants();

    Tenant getTenantById(Long id);
}