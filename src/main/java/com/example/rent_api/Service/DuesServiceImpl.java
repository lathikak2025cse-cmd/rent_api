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

    // =====================================================
    // CHECK DUES FOR ONE TENANT
    // =====================================================

    @Override
    public String getTenantDues(Long tenantId) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() ->
                        new RuntimeException("Tenant not found"));

        // Tenant must have a room
        if (tenant.getRoom() == null) {
            throw new RuntimeException(
                    "Tenant is not assigned to any room");
        }

        // Move-in date is required
        if (tenant.getMoveInDate() == null) {
            throw new RuntimeException(
                    "Tenant move-in date is not available");
        }

        LocalDate moveInDate =tenant.getMoveInDate();

        LocalDate currentDate =LocalDate.now();

        Period period = Period.between(moveInDate,currentDate);

        int monthsSinceMoveIn = period.getYears() * 12 + period.getMonths()+ 1;

        // Get all payments for this tenant
        List<RentPayment> payments = rentPaymentRepository.findAll().stream()
                        .filter(payment -> payment.getTenant() != null && payment.getTenant()
                        .getId().equals(tenantId)).toList();

        int monthsPaid = payments.size();

        int pendingMonths = monthsSinceMoveIn - monthsPaid;

        if (pendingMonths < 0) {
            pendingMonths = 0;
        }

        double monthlyRent =tenant.getRoom().getMonthlyRent();

        double pendingAmount =pendingMonths * monthlyRent;

        return "Tenant: "
                + tenant.getName()
                + "\nMonths Since Move-in: "
                + monthsSinceMoveIn
                + "\nMonths Paid: "
                + monthsPaid
                + "\nPending Months: "
                + pendingMonths
                + "\nPending Amount: ₹"
                + pendingAmount;
    }


    // =====================================================
    // CURRENT MONTH PENDING RENT
    // =====================================================

    @Override
    public List<Tenant>
            getTenantsWithPendingCurrentMonthRent() {

        String currentMonth = LocalDate.now().toString().substring(0, 7);

        return tenantRepository.findAll().stream()
                // Only tenants who have a room
               .filter(tenant -> tenant.getRoom() != null)
                // Check whether tenant has paid
                // the current month's rent
                .filter(tenant -> {

     List<RentPayment> payments =rentPaymentRepository.findAllByTenantIdAndMonth(
                                            tenant.getId(),
                                            currentMonth);

                    return payments.isEmpty();
                })

                .toList();
    }
}