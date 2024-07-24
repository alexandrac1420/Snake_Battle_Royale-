package com.example.SnakeProyect.controller;

import com.example.SnakeProyect.model.GameRoom;
import com.example.SnakeProyect.service.GameRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gamerooms")
public class GameRoomController {
    @Autowired
    private GameRoomService gameRoomService;

    @PostMapping("/create")
    public GameRoom createGameRoom(@RequestBody GameRoomRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String creator = authentication.getName();
        return gameRoomService.createGameRoom(request.getName(), request.getMaxPlayers(), creator);
    }

    @GetMapping("/available")
    public List<GameRoom> getAvailableGameRooms() {
        return gameRoomService.getAvailableGameRooms();
    }

    @PostMapping("/join")
    public GameRoom joinGameRoom(@RequestParam String gameId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String playerName = authentication.getName();
        return gameRoomService.joinGameRoom(gameId, playerName);
    }

    @PostMapping("/update-ready-status")
    public void updateReadyStatus(@RequestParam String gameId, @RequestParam boolean isReady) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String playerName = authentication.getName();
        gameRoomService.updateReadyStatus(gameId, playerName, isReady);
    }

    @GetMapping("/players")
    public List<String> getPlayersInGameRoom(@RequestParam String gameId) {
        return gameRoomService.getPlayersInGameRoom(gameId);
    }

    @GetMapping("/ready-status")
    public List<Boolean> getReadyStatusInGameRoom(@RequestParam String gameId) {
        return gameRoomService.getReadyStatusInGameRoom(gameId);
    }

    @GetMapping("/can-start")
    public boolean canStartGame(@RequestParam String gameId) {
        return gameRoomService.canStartGame(gameId);
    }
}
