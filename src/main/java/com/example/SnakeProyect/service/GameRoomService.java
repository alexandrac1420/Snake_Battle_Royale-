package com.example.SnakeProyect.service;

import com.example.SnakeProyect.model.GameRoom;
import com.example.SnakeProyect.repository.GameRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameRoomService {
    @Autowired
    private GameRoomRepository gameRoomRepository;

    public GameRoom createGameRoom(String name, int maxPlayers, String creator) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.setName(name);
        gameRoom.setMaxPlayers(maxPlayers);
        List<String> players = new ArrayList<>();
        players.add(creator); // Agrega al creador a la lista de jugadores
        gameRoom.setPlayers(players);
        List<Boolean> readyStatus = new ArrayList<>();
        readyStatus.add(false); // El creador no está listo inicialmente
        gameRoom.setReadyStatus(readyStatus);
        return gameRoomRepository.save(gameRoom);
    }

    public List<GameRoom> getAvailableGameRooms() {
        return gameRoomRepository.findByNotStarted();
    }

    public GameRoom joinGameRoom(String gameId, String playerName) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow();
        if (gameRoom.getPlayers().size() < gameRoom.getMaxPlayers() && !gameRoom.getPlayers().contains(playerName)) { // Verifica si la sala no está llena
            gameRoom.getPlayers().add(playerName);
            gameRoom.getReadyStatus().add(false);
        }
        return gameRoomRepository.save(gameRoom);
    }

    public void updateReadyStatus(String gameId, String playerName, boolean isReady) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow();
        int playerIndex = gameRoom.getPlayers().indexOf(playerName);
        gameRoom.getReadyStatus().set(playerIndex, isReady);
        gameRoomRepository.save(gameRoom);
    }

    public List<String> getPlayersInGameRoom(String gameId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow();
        return gameRoom.getPlayers();
    }

    public List<Boolean> getReadyStatusInGameRoom(String gameId) {
        GameRoom gameRoom = gameRoomRepository.findById(gameId).orElseThrow();
        return gameRoom.getReadyStatus();
    }

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
