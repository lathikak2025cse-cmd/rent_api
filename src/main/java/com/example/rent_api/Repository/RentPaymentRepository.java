package com.example.rent_api.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rent_api.Model.RentPayment;

public interface RentPaymentRepository
        extends JpaRepository<RentPayment, Long> {

    List<RentPayment> findAllByTenantIdAndMonth(
            Long tenantId,
            String month);
}