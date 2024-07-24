package com.example.SnakeProyect.controller;

import com.example.SnakeProyect.model.GameRoom;
import com.example.SnakeProyect.service.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing game rooms.
 */
@RestController
@RequestMapping("/api/gamerooms")
public class GameRoomController {
    
    @Autowired
    private GameRoomService gameRoomService;

    /**
     * Creates a new game room.
     * @param request the request containing game room details.
     * @return the created GameRoom object.
     */
    @PostMapping("/create")
    public GameRoom createGameRoom(@RequestBody GameRoomRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String creator = authentication.getName();
        return gameRoomService.createGameRoom(request.getName(), request.getMaxPlayers(), creator);
    }

    /**
     * Retrieves a list of available game rooms.
     * @return a list of available GameRoom objects.
     */
    @GetMapping("/available")
    public List<GameRoom> getAvailableGameRooms() {
        return gameRoomService.getAvailableGameRooms();
    }

    /**
     * Allows a player to join an existing game room.
     * @param gameId the ID of the game room to join.
     * @return the updated GameRoom object.
     */
    @PostMapping("/join")
    public GameRoom joinGameRoom(@RequestParam String gameId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String playerName = authentication.getName();
        return gameRoomService.joinGameRoom(gameId, playerName);
    }

    /**
     * Updates the ready status of a player in a game room.
     * @param gameId the ID of the game room.
     * @param isReady the ready status of the player.
     */
    @PostMapping("/update-ready-status")
    public void updateReadyStatus(@RequestParam String gameId, @RequestParam boolean isReady) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String playerName = authentication.getName();
        gameRoomService.updateReadyStatus(gameId, playerName, isReady);
    }

    /**
     * Retrieves a list of players in a specific game room.
     * @param gameId the ID of the game room.
     * @return a list of player names in the game room.
     */
    @GetMapping("/players")
    public List<String> getPlayersInGameRoom(@RequestParam String gameId) {
        return gameRoomService.getPlayersInGameRoom(gameId);
    }

    /**
     * Retrieves the ready status of players in a specific game room.
     * @param gameId the ID of the game room.
     * @return a list of ready statuses for players in the game room.
     */
    @GetMapping("/ready-status")
    public List<Boolean> getReadyStatusInGameRoom(@RequestParam String gameId) {
        return gameRoomService.getReadyStatusInGameRoom(gameId);
    }

    /**
     * Checks if a game can be started in a specific game room.
     * @param gameId the ID of the game room.
     * @return true if the game can be started, false otherwise.
     */
    @GetMapping("/can-start")
    public boolean canStartGame(@RequestParam String gameId) {
        return gameRoomService.canStartGame(gameId);
    }
}
