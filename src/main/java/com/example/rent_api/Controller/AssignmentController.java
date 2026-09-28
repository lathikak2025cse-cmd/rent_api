package com.example.rent_api.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.rent_api.Service.AssignmentService;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public String assignRoom(
            @RequestParam Long tenantId,
            @RequestParam Long roomId) {

        return assignmentService.assignRoom(tenantId, roomId);
    }
}