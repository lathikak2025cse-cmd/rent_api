package com.example.rent_api.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rent_api.Model.RentPayment;

public interface RentPaymentRepository extends JpaRepository<RentPayment, Long> {

    Optional<RentPayment> findByTenantIdAndMonth(Long tenantId, String month);
}