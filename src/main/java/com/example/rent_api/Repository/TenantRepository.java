package com.example.rent_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rent_api.Model.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

}