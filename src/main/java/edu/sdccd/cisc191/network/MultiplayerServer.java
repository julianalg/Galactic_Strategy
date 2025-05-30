package edu.sdccd.cisc191.network;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

    /*
     * Features added:
     * Multiplayer server for handling multiple player connections
     * Creates a server that listens for connections
     * Accepts multiple players
     * Broadcasts messages to all connected client
     */

public class MultiplayerServer {
    private static final int PORT = 5000; // Port for communication
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    // TODO: replace System.out.println calls with loggers
    public static void main(String[] args) {
        System.out.println("Multiplayer Server Started...");
        try (ServerSocket serverSocket = new ServerSocket (PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("server Error: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                System.out.println("New Player Connected: " + socket.getInetAddress());

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);
                    broadcastMessage("Player: " + message);
                }
            } catch (IOException e) {
                System.err.println("Connection Lost: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
            }
        }
    }

    private static void broadcastMessage(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }
}
