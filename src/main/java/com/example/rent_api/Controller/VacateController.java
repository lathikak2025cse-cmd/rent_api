package com.example.rent_api.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.rent_api.Service.VacateService;

@RestController
@RequestMapping("/api/vacate")
public class VacateController {

    private final VacateService vacateService;

    public VacateController(VacateService vacateService) {
        this.vacateService = vacateService;
    }
    //update 
    @PutMapping("/tenant/{tenantId}")
    public String vacateTenant(@PathVariable Long tenantId) {

        return vacateService.vacateTenant(tenantId);
    }
}