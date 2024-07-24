package com.example.SnakeProyect.repository;

import com.example.SnakeProyect.model.GameRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GameRoomRepository extends MongoRepository<GameRoom, String> {
    @Query("{ 'started': false }")
    List<GameRoom> findByNotStarted();
}
