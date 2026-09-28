package com.example.rent_api.Service;

import java.util.List;

import com.example.rent_api.Model.Tenant;

public interface DuesService {

    String getTenantDues(Long tenantId);

    List<Tenant> getTenantsWithPendingCurrentMonthRent();
}