package com.example.rent_api.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.rent_api.Model.Tenant;
import com.example.rent_api.Service.DuesService;

@RestController
@RequestMapping("/api/dues")
public class DuesController {

    private final DuesService duesService;

    public DuesController(DuesService duesService) {
        this.duesService = duesService;
    }

    @GetMapping("/tenant/{tenantId}")
    public String getTenantDues(
            @PathVariable Long tenantId) {

        return duesService.getTenantDues(tenantId);
    }

    @GetMapping("/current-month")
    public List<Tenant> getTenantsWithPendingCurrentMonthRent() {

        return duesService.getTenantsWithPendingCurrentMonthRent();
    }
}