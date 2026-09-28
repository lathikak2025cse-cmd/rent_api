package com.example.rent_api.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.rent_api.Model.Room;
import com.example.rent_api.Service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public Room addRoom(@RequestBody Room room) {

        return roomService.addRoom(room);
    }

    @GetMapping
    public List<Room> getAllRooms() {

        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {

        return roomService.getRoomById(id);
    }
}