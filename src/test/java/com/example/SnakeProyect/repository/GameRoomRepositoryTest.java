package com.example.SnakeProyect.repository;

import com.example.SnakeProyect.model.GameRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class GameRoomRepositoryTest {

    @Autowired
    private GameRoomRepository gameRoomRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        // Clean up collection before each test
        mongoTemplate.dropCollection(GameRoom.class);
    }

    @Test
    void testFindByNotStarted() {
        // Given
        GameRoom gameRoom1 = new GameRoom();
        gameRoom1.setStarted(false);
        gameRoomRepository.save(gameRoom1);

        GameRoom gameRoom2 = new GameRoom();
        gameRoom2.setStarted(true);
        gameRoomRepository.save(gameRoom2);

        // When
        List<GameRoom> result = gameRoomRepository.findByNotStarted();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).isStarted()).isFalse();
    }
}
