package com.example.rent_api.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.rent_api.Model.RentPayment;
import com.example.rent_api.Model.Tenant;
import com.example.rent_api.Repository.RentPaymentRepository;
import com.example.rent_api.Repository.TenantRepository;

@Service
public class DuesServiceImpl implements DuesService {

    private final TenantRepository tenantRepository;
    private final RentPaymentRepository rentPaymentRepository;

    public DuesServiceImpl(
            TenantRepository tenantRepository,
            RentPaymentRepository rentPaymentRepository) {

        this.tenantRepository = tenantRepository;
        this.rentPaymentRepository = rentPaymentRepository;
    }

    @Override
    public String getTenantDues(Long tenantId) {

        // Find tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        // Check whether tenant has a room
        if (tenant.getRoom() == null) {
            throw new RuntimeException(
                    "Tenant is not assigned to any room");
        }

        // Get move-in date
        LocalDate moveInDate = tenant.getMoveInDate();

        // Current date
        LocalDate currentDate = LocalDate.now();

        // Calculate number of months since move-in
        Period period = Period.between(moveInDate, currentDate);

        int monthsSinceMoveIn =
                period.getYears() * 12 + period.getMonths() + 1;

        // Get number of payments made by tenant
        List<RentPayment> payments =
                rentPaymentRepository.findAll()
                        .stream()
                        .filter(payment ->
                                payment.getTenant().getId().equals(tenantId))
                        .toList();

        int monthsPaid = payments.size();

        // Calculate pending months
        int pendingMonths = monthsSinceMoveIn - monthsPaid;

        if (pendingMonths < 0) {
            pendingMonths = 0;
        }

        // Monthly rent
        double monthlyRent = tenant.getRoom().getMonthlyRent();

        // Calculate pending amount
        double pendingAmount = pendingMonths * monthlyRent;

        return "Tenant: " + tenant.getName()
                + "\nMonths since move-in: " + monthsSinceMoveIn
                + "\nMonths paid: " + monthsPaid
                + "\nPending months: " + pendingMonths
                + "\nPending amount: ₹" + pendingAmount;
    }

    @Override
    public List<Tenant> getTenantsWithPendingCurrentMonthRent() {

        String currentMonth =
                LocalDate.now().toString().substring(0, 7);

        return tenantRepository.findAll()
                .stream()
                .filter(tenant -> tenant.getRoom() != null)
                .filter(tenant ->
                        !rentPaymentRepository
                                .findByTenantIdAndMonth(
                                        tenant.getId(),
                                        currentMonth)
                                .isPresent())
                .toList();
    }
}