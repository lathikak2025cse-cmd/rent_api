package com.example.rent_api.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.rent_api.Model.RentPayment;
import com.example.rent_api.Model.Tenant;
import com.example.rent_api.Repository.RentPaymentRepository;
import com.example.rent_api.Repository.TenantRepository;

@Service
public class RentPaymentServiceImpl implements RentPaymentService {

    private final RentPaymentRepository rentPaymentRepository;
    private final TenantRepository tenantRepository;

    public RentPaymentServiceImpl(
            RentPaymentRepository rentPaymentRepository,
            TenantRepository tenantRepository) {

        this.rentPaymentRepository = rentPaymentRepository;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public RentPayment makePayment(Long tenantId, RentPayment payment) {

        // Find tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        // Check whether rent is already paid for this month
        if (rentPaymentRepository
                .findByTenantIdAndMonth(tenantId, payment.getMonth())
                .isPresent()) {

            throw new RuntimeException(
                    "Rent for this month is already paid");
        }

        // Connect payment with tenant
        payment.setTenant(tenant);

        // Set payment date automatically
        payment.setPaymentDate(LocalDate.now());

        // Save payment
        return rentPaymentRepository.save(payment);
    }

    @Override
    public List<RentPayment> getAllPayments() {

        return rentPaymentRepository.findAll();
    }

    @Override
    public List<RentPayment> getPaymentsByTenant(Long tenantId) {

        return rentPaymentRepository.findAll()
                .stream()
                .filter(payment -> payment.getTenant().getId().equals(tenantId))
                .toList();
    }
}