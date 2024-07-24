package com.example.SnakeProyect.repository;

import com.example.SnakeProyect.model.GameRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Repository interface for managing GameRoom entities in MongoDB.
 */
public interface GameRoomRepository extends MongoRepository<GameRoom, String> {

    /**
     * Finds all game rooms that have not started yet.
     * @return a list of GameRoom objects where the 'started' field is false.
     */
    @Query("{ 'started': false }")
    List<GameRoom> findByNotStarted();
}
