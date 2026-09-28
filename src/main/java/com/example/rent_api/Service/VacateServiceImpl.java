package com.example.rent_api.Service;

import org.springframework.stereotype.Service;

import com.example.rent_api.Model.Room;
import com.example.rent_api.Model.Tenant;
import com.example.rent_api.Repository.RoomRepository;
import com.example.rent_api.Repository.TenantRepository;

@Service
public class VacateServiceImpl implements VacateService {

    private final TenantRepository tenantRepository;
    private final RoomRepository roomRepository;

    public VacateServiceImpl(
            TenantRepository tenantRepository,
            RoomRepository roomRepository) {

        this.tenantRepository = tenantRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public String vacateTenant(Long tenantId) {

        // Step 1: Find tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        // Step 2: Check whether tenant has a room
        if (tenant.getRoom() == null) {
            throw new RuntimeException(
                    "Tenant is not assigned to any room");
        }

        // Step 3: Get the assigned room
        Room room = tenant.getRoom();

        // Step 4: Remove room from tenant
        tenant.setRoom(null);

        // Step 5: Make room available
        room.setOccupied(false);

        // Step 6: Save changes
        tenantRepository.save(tenant);
        roomRepository.save(room);

        return "Tenant vacated successfully";
    }
}