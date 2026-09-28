package com.example.rent_api.Service;

import org.springframework.stereotype.Service;

import com.example.rent_api.Model.Room;
import com.example.rent_api.Model.Tenant;
import com.example.rent_api.Repository.RoomRepository;
import com.example.rent_api.Repository.TenantRepository;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final TenantRepository tenantRepository;
    private final RoomRepository roomRepository;

    public AssignmentServiceImpl(
            TenantRepository tenantRepository,
            RoomRepository roomRepository) {

        this.tenantRepository = tenantRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public String assignRoom(Long tenantId, Long roomId) {

        // Find tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        // Find room
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Check whether tenant already has a room
        if (tenant.getRoom() != null) {
            throw new RuntimeException(
                    "Tenant is already assigned to a room");
        }

        // Check whether room is occupied
        if (room.isOccupied()) {
            throw new RuntimeException(
                    "Room is already occupied");
        }

        // Assign room to tenant
        tenant.setRoom(room);

        // Mark room as occupied
        room.setOccupied(true);

        // Save changes
        tenantRepository.save(tenant);
        roomRepository.save(room);

        return "Room assigned successfully";
    }
}