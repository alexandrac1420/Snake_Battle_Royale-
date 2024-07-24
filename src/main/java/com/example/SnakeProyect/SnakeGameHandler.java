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

/**
 * WebSocket handler for managing real-time communication in the Snake game.
 */
@Component
public class SnakeGameHandler extends TextWebSocketHandler {
    // List to keep track of all WebSocket sessions
    private final List<WebSocketSession> sessions = new ArrayList<>();
    // Service for managing game logic
    private final SnakeGameService snakeGameService = new SnakeGameService();
    // Object mapper for JSON serialization and deserialization
    private final ObjectMapper objectMapper = new ObjectMapper();
    // Chunk size for splitting large messages
    private final int CHUNK_SIZE = 1024;
    // Buffers to accumulate messages for each session
    private final Map<String, StringBuilder> sessionMessageBuffers = new HashMap<>();
    // Mapping of session IDs to snake IDs
    private final Map<String, String> sessionToSnakeMap = new HashMap<>();

    /**
     * Called after a WebSocket connection has been established.
     * 
     * @param session The WebSocket session that was established.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Add the new session to the list of sessions
        sessions.add(session);
        sessionMessageBuffers.put(session.getId(), new StringBuilder());
        System.out.println("Connection Established: " + session.getId());

        // Assign a unique snake ID to the new player
        String snakeId = "snake" + session.getId();
        sessionToSnakeMap.put(session.getId(), snakeId);
        snakeGameService.addSnakeToGame(snakeId);

        // Send the initial game state to all connected clients
        broadcastGameState(snakeGameService.getGameState());
    }

    /**
     * Handles incoming text messages from a WebSocket session.
     * 
     * @param session The WebSocket session from which the message was received.
     * @param message The text message received from the session.
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String sessionId = session.getId();
            sessionMessageBuffers.get(sessionId).append(message.getPayload());
            System.out.println("Received message from session " + sessionId + ": " + message.getPayload());

            String completeMessage = sessionMessageBuffers.get(sessionId).toString();
            try {
                if (completeMessage.contains("direction")) {
                    MoveRequest moveRequest = objectMapper.readValue(completeMessage, MoveRequest.class);
                    sessionMessageBuffers.put(sessionId, new StringBuilder()); // Clear buffer after successful parsing
                    String snakeId = sessionToSnakeMap.get(sessionId);
                    System.out.println("Move request for snake " + snakeId + ": " + moveRequest.getDirection());
                    snakeGameService.moveSnake(snakeId, moveRequest.getDirection());
                } else if (completeMessage.contains("ready")) {
                    ReadyRequest readyRequest = objectMapper.readValue(completeMessage, ReadyRequest.class);
                    sessionMessageBuffers.put(sessionId, new StringBuilder()); // Clear buffer after successful parsing
                    String snakeId = sessionToSnakeMap.get(sessionId);
                    System.out.println("Ready request for snake " + snakeId);
                    snakeGameService.setPlayerReady(snakeId);
                }

                // Broadcast the updated game state to all clients
                broadcastGameState(snakeGameService.getGameState());
            } catch (Exception e) {
                System.out.println("Error parsing message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error handling message: " + e.getMessage());
        }
    }

    /**
     * Called after a WebSocket connection has been closed.
     * 
     * @param session The WebSocket session that was closed.
     * @param status  The status of the connection close.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove the session from the list and clean up resources
        sessions.remove(session);
        sessionMessageBuffers.remove(session.getId());
        String snakeId = sessionToSnakeMap.remove(session.getId());
        System.out.println("Connection Closed: " + session.getId());

        // Remove the snake from the game and update all clients
        if (snakeId != null) {
            snakeGameService.removeSnakeFromGame(snakeId);
            broadcastGameState(snakeGameService.getGameState());
        }
    }

    /**
     * Sends a large message in chunks to a WebSocket session.
     * 
     * @param session The WebSocket session to which the message is sent.
     * @param data    The message data to be sent.
     * @throws Exception If an error occurs while sending the message.
     */
    private synchronized void sendInChunks(WebSocketSession session, String data) throws Exception {
        int start = 0;
        while (start < data.length()) {
            int end = Math.min(data.length(), start + CHUNK_SIZE);
            session.sendMessage(new TextMessage(data.substring(start, end)));
            start = end;
        }
    }

    /**
     * Broadcasts the current game state to all connected WebSocket sessions.
     * 
     * @param gameState The current game state to be broadcasted.
     * @throws Exception If an error occurs while broadcasting the game state.
     */
    private synchronized void broadcastGameState(SnakeGame gameState) throws Exception {
        String gameStateString = objectMapper.writeValueAsString(gameState);
        for (WebSocketSession webSocketSession : sessions) {
            sendInChunks(webSocketSession, gameStateString);
        }
    }
}
