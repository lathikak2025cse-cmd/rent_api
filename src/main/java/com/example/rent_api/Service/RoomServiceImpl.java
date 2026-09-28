package com.example.rent_api.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.rent_api.Model.Room;
import com.example.rent_api.Repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room addRoom(Room room) {

        room.setOccupied(false);

        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {

        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {

        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }
}