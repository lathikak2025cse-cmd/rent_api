package com.example.rent_api.Service;

import java.util.List;

import com.example.rent_api.Model.Room;

public interface RoomService {

    Room addRoom(Room room);

    List<Room> getAllRooms();

    Room getRoomById(Long id);
}
