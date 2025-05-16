package edu.sdccd.cisc191.network;

import java.net.Socket;

public class PlayerData {
    private final String playerId;
    private final String playerName;
    private Socket socket;

    // Additional fields such as score, position, etc., can be added here.

    public PlayerData(String playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public String getPlayerId() {
        try {
            Socket socket = this.socket;
            if (socket != null) {
                return socket.getInetAddress().getHostAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "playerId='" + playerId + '\'' +
                ", playerName='" + playerName + '\'' +
                '}';
    }
}
