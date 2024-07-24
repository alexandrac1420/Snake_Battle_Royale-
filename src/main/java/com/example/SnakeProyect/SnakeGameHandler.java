package com.example.SnakeProyect;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.example.SnakeProyect.model.MoveRequest;
import com.example.SnakeProyect.model.ReadyRequest;
import com.example.SnakeProyect.model.SnakeGame;
import com.example.SnakeProyect.service.SnakeGameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Component
public class SnakeGameHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final SnakeGameService snakeGameService = new SnakeGameService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final int CHUNK_SIZE = 1024;
    private final Map<String, StringBuilder> sessionMessageBuffers = new HashMap<>();
    private final Map<String, String> sessionToSnakeMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        sessionMessageBuffers.put(session.getId(), new StringBuilder());
        System.out.println("Connection Established: " + session.getId());

        // Assign a unique snake ID to the new player
        String snakeId = "snake" + session.getId();
        sessionToSnakeMap.put(session.getId(), snakeId);
        snakeGameService.addSnakeToGame(snakeId);

        // Send the initial game state to all clients
        broadcastGameState(snakeGameService.getGameState());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String sessionId = session.getId();
            sessionMessageBuffers.get(sessionId).append(message.getPayload());

            // Try to parse the accumulated message
            String completeMessage = sessionMessageBuffers.get(sessionId).toString();
            try {
                if (completeMessage.contains("direction")) {
                    MoveRequest moveRequest = objectMapper.readValue(completeMessage, MoveRequest.class);
                    sessionMessageBuffers.put(sessionId, new StringBuilder()); // Clear buffer after successful parsing
                    String snakeId = sessionToSnakeMap.get(sessionId);
                    snakeGameService.moveSnake(snakeId, moveRequest.getDirection());
                } else if (completeMessage.contains("ready")) {
                    ReadyRequest readyRequest = objectMapper.readValue(completeMessage, ReadyRequest.class);
                    sessionMessageBuffers.put(sessionId, new StringBuilder()); // Clear buffer after successful parsing
                    String snakeId = sessionToSnakeMap.get(sessionId);
                    snakeGameService.setPlayerReady(snakeId);
                }

                broadcastGameState(snakeGameService.getGameState());
            } catch (Exception e) {
                // If parsing fails, wait for more fragments
                System.out.println("Waiting for more message fragments...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        sessionMessageBuffers.remove(session.getId());
        String snakeId = sessionToSnakeMap.remove(session.getId());
        System.out.println("Connection Closed: " + session.getId());

        if (snakeId != null) {
            snakeGameService.removeSnakeFromGame(snakeId);
            broadcastGameState(snakeGameService.getGameState());
        }
    }

    private synchronized void sendInChunks(WebSocketSession session, String data) throws Exception {
        int start = 0;
        while (start < data.length()) {
            int end = Math.min(data.length(), start + CHUNK_SIZE);
            session.sendMessage(new TextMessage(data.substring(start, end)));
            start = end;
        }
    }

    private synchronized void broadcastGameState(SnakeGame gameState) throws Exception {
        String gameStateString = objectMapper.writeValueAsString(gameState);
        for (WebSocketSession webSocketSession : sessions) {
            sendInChunks(webSocketSession, gameStateString);
        }
    }
}
