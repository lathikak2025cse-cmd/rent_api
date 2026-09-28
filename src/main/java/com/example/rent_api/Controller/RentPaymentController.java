package com.example.rent_api.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.rent_api.Model.RentPayment;
import com.example.rent_api.Service.RentPaymentService;

@RestController
@RequestMapping("/api/payments")
public class RentPaymentController {

    private final RentPaymentService rentPaymentService;

    public RentPaymentController(RentPaymentService rentPaymentService) {
        this.rentPaymentService = rentPaymentService;
    }

    @PostMapping
    public RentPayment makePayment(
            @RequestParam Long tenantId,
            @RequestBody RentPayment payment) {

        return rentPaymentService.makePayment(tenantId, payment);
    }

    @GetMapping
    public List<RentPayment> getAllPayments() {

        return rentPaymentService.getAllPayments();
    }

    @GetMapping("/tenant/{tenantId}")
    public List<RentPayment> getPaymentsByTenant(
            @PathVariable Long tenantId) {

        return rentPaymentService.getPaymentsByTenant(tenantId);
    }
}