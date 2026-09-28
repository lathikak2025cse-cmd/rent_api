package com.example.rent_api.Service;

import java.util.List;

import com.example.rent_api.Model.RentPayment;

public interface RentPaymentService {

    RentPayment makePayment(Long tenantId, RentPayment payment);

    List<RentPayment> getAllPayments();

    List<RentPayment> getPaymentsByTenant(Long tenantId);
}