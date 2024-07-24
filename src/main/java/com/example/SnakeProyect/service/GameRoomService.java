package com.example.SnakeProyect.service;

import com.example.SnakeProyect.model.GameRoom;
import com.example.SnakeProyect.repository.GameRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing game rooms.
 */
@Service
public class GameRoomService {

    private final GameRoomRepository gameRoomRepository;

    /**
     * Constructor that injects the GameRoomRepository.
     * @param gameRoomRepository the repository for managing GameRoom entities.
     */
    @Autowired
    public GameRoomService(GameRoomRepository gameRoomRepository) {
        this.gameRoomRepository = gameRoomRepository;
    }

    /**
     * Creates a new game room.
     * @param name the name of the game room.
     * @param maxPlayers the maximum number of players allowed in the game room.
     * @param creator the username of the creator of the game room.
     * @return the created GameRoom object.
     */
    public GameRoom createGameRoom(String name, int maxPlayers, String creator) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.setName(name);
        gameRoom.setMaxPlayers(maxPlayers);
        List<String> players = new ArrayList<>();
        players.add(creator); // Adds the creator to the list of players
        gameRoom.setPlayers(players);
        List<Boolean> readyStatus = new ArrayList<>();
        readyStatus.add(false); // The creator is not ready initially
        gameRoom.setReadyStatus(readyStatus);
        return gameRoomRepository.save(gameRoom);
    }

    /**
     * Retrieves a list of available game rooms that have not started yet.
     * @return a list of GameRoom objects where the game has not started.
     */
    public List<GameRoom> getAvailableGameRooms() {
        return gameRoomRepository.findByNotStarted();
    }

    /**
     * Adds a player to an existing game room.
     * @param gameId the ID of the game room to join.
     * @param playerName the name of the player joining the game room.
     * @return the updated GameRoom object.
     */
    public GameRoom joinGameRoom(String gameId, String playerName) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow();
        if (gameRoom.getPlayers().size() < gameRoom.getMaxPlayers() && !gameRoom.getPlayers().contains(playerName)) { // Checks if the room is not full
            gameRoom.getPlayers().add(playerName);
            gameRoom.getReadyStatus().add(false);
        }
        return gameRoomRepository.save(gameRoom);
    }

    /**
     * Updates the ready status of a player in a game room.
     * @param gameId the ID of the game room.
     * @param playerName the name of the player whose status is being updated.
     * @param isReady the new ready status of the player.
     */
    public void updateReadyStatus(String gameId, String playerName, boolean isReady) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow();
        int playerIndex = gameRoom.getPlayers().indexOf(playerName);
        gameRoom.getReadyStatus().set(playerIndex, isReady);
        gameRoomRepository.save(gameRoom);
    }

    /**
     * Retrieves a list of players in a specific game room.
     * @param gameId the ID of the game room.
     * @return a list of player names in the game room.
     */
    public List<String> getPlayersInGameRoom(String gameId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow();
        return gameRoom.getPlayers();
    }

    /**
     * Retrieves the ready status of players in a specific game room.
     * @param gameId the ID of the game room.
     * @return a list of booleans representing the ready status of each player.
     */
    public List<Boolean> getReadyStatusInGameRoom(String gameId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow();
        return gameRoom.getReadyStatus();
    }

    /**
     * Checks if the game in a specific game room can start.
     * @param gameId the ID of the game room.
     * @return true if the game can start (i.e., all players are ready and the room is full); false otherwise.
     */
    public boolean canStartGame(String gameId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow();
        if (gameRoom.getPlayers().size() == gameRoom.getMaxPlayers()) {
            for (Boolean status : gameRoom.getReadyStatus()) {
                if (!status) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
