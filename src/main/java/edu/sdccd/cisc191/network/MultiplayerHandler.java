package edu.sdccd.cisc191.network;

import org.mortbay.jetty.security.ClientCertAuthenticator;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

/*
 * Features added:
 * - Manages connected players and their data
 * - Handles player-specific actions (e.g., joining, leaving, and in-game updates)
 * - Provides utility methods for managing game state
 */

// TODO: delete or implement unused Server code
public class MultiplayerHandler {

    public final Map<String, PlayerData> players; // Map to store player data by unique ID

    public void initializeServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server initialized on port: " + port);
        } catch (IOException e) {
            System.err.println("Failed to initialize server: " + e.getMessage());
        }

        try {
            ClientCertAuthenticator authenticator = new ClientCertAuthenticator();
            System.out.println("Client cert authenticator initialized");
        } catch (Exception e) {
            System.err.println("Failed to initialize client cert authenticator: " + e.getMessage());
        }
    }


    public MultiplayerHandler(ServerSocket serverSocket) {
        ClientCertAuthenticator authenticator = new ClientCertAuthenticator();
        this.players = new HashMap<>();
    }

    /**
     * Adds a new player to the game.
     *
     * @param playerId Unique ID for the player
     * @param playerName Name of the player
     */
    public synchronized void addPlayer(String playerId, String playerName) {
        if (!players.containsKey(playerId)) {
            players.put(playerId, new PlayerData(playerId, playerName));
            System.out.println("Player added: " + playerName + " (ID: " + playerId + ")");
        } else {
            System.out.println("Player with ID " + playerId + " already exists.");
        }
    }

    /**
     * Removes a player from the game.
     *
     * @param playerId Unique ID for the player
     */
    public synchronized void removePlayer(String playerId) {
        if (players.containsKey(playerId)) {
            PlayerData removedPlayer = players.remove(playerId);
            System.out.println("Player removed: " + removedPlayer.getPlayerName() + " (ID: " + playerId + ")");
        } else {
            System.out.println("No player found with ID: " + playerId);
        }
    }

    /**
     * Updates a player's data (e.g., position or score).
     *
     * @param playerId Unique ID for the player
     * @param newData Updated data for the player
     */
    public synchronized void updatePlayerData(String playerId, PlayerData newData) {
        if (players.containsKey(playerId)) {
            players.put(playerId, newData);
            System.out.println("Updated data for player: " + newData.getPlayerName());
        } else {
            players.put(playerId, new PlayerData(playerId, newData.getPlayerName()));
            System.out.println("No player found with ID: " + playerId);
        }
    }

    /**
     * Retrieves a player's data.
     *
     * @param playerId Unique ID for the player
     */
    public synchronized void getPlayerData(String playerId) {
        if (players.containsKey(playerId)) {
            getPlayerData(playerId);
            players.get(playerId);
            return;
        } else {
            System.out.println("No player found with ID: " + playerId);
        }
        players.get(playerId);
    }

    /**
     * Broadcasts a message to all players (for debugging or notifications).
     *
     * @param message The message to broadcast
     */
    public synchronized void broadcastMessage(String message) {
        System.out.println("Broadcasting message to all players: " + message);
        // In a real implementation, this would send messages to connected clients.
    }
}