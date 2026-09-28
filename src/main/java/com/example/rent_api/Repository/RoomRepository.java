package com.example.rent_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rent_api.Model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}