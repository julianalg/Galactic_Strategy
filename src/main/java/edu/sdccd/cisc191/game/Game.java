package edu.sdccd.cisc191.game;

import edu.sdccd.cisc191.subsystems.CombatSystem;
import edu.sdccd.cisc191.subsystems.ExplorationSystem;
import edu.sdccd.cisc191.subsystems.ResourceManagement;
import edu.sdccd.cisc191.game.PlayerInventory;

import javafx.animation.AnimationTimer; // 1 Game loop that calls handle() method
import javafx.application.Application;  // 2 JavaFX application base class
import javafx.scene.Scene;              // 3 Container for all content in a scene graph
import javafx.scene.control.*;      // 4 IU control text display
import javafx.scene.input.KeyCode;      // 5 Provides key codes to handle keyboard input
import javafx.stage.Stage;              // 7 Top-level window

import javafx.scene.control.ListView;                   // 8
import javafx.scene.layout.VBox;                        // 9
import javafx.scene.control.Button;                     // 10

import java.util.concurrent.Executors;                  // 11
import java.util.concurrent.ScheduledExecutorService;   // 12
import java.util.concurrent.TimeUnit;                   // 13



// Main Game Class (Integrates JavaFX, Shipyard System, and Exploration System)
public class Game extends Application {
    private Shipyard shipyard;
    private ExplorationSystem explorationSystem;
    private ResourceManagement resourceManagement;
    private Player player;
    private PlayerInventory inventory;

    private ListView<String> fleetListView;     // IUO List for displaying player's fleet
    private Label statusLabel;                  // Status label
    private TextArea gameLog;
    private ComboBox<String> planetSelector;// Field to store the current state of the game
    private Label resourceLabel;

    private GameState gameState;

    public enum GameState {                     // Define an enumeration for different game states
        MENU, PLAYING, PAUSED, GAME_OVER
    }

    public static void main(String[] args) {    // Launch the JavaFX application
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) {    // Main window
        // Initialize game components
        shipyard = new Shipyard();
        explorationSystem = new ExplorationSystem();
        resourceManagement = new ResourceManagement();
        inventory = new PlayerInventory();
        player = new Player ("Captain");

        gameState = GameState.MENU;

        // Create UI elements
        statusLabel = new Label("Welcome to Galactic Strategy! (Press ENTER to start)");
        fleetListView = new ListView<>();
        updateFleetDisplay();   // Load fleet data

        gameLog = new TextArea();
        gameLog.setEditable(false);
        gameLog.setPrefHeight(150);

        resourceLabel = new Label("Resources:\n" + inventory.displayResources());

        // Shipyard UI Buttons
        Button buildFighterBtn = new Button("Build Fighter (10 Minerals, 5 Energy)");
        Button buildCruiserBtn = new Button("Build Cruiser (15 Minerals, 7 Energy)");
        Button buildBattleshipBtn = new Button("Build Battleship (20 Minerals, 10 Energy)");
        Button upgradeShipBtn = new Button("Upgrade Selected Ship");

        // Resource Gathering Button
        Button gatherDilithiumBtn = new Button("Gather Dilithium");
        gatherDilithiumBtn.setOnAction(e -> gatherDilithium());

        // Exploration UI
        Button exploreBtn = new Button("Explore Planet");
        planetSelector = new ComboBox<>();
        planetSelector.getItems().addAll("Mars", "Jupiter", "Neptune", "Alpha Centauri", "Andromeda");
        planetSelector.setValue("Mars"); // Default selection

        // Assign button actions
        buildFighterBtn.setOnAction(e -> buildShip("Fighter", 10, 5));
        buildCruiserBtn.setOnAction(e -> buildShip("Cruiser", 15, 7));
        buildBattleshipBtn.setOnAction(e -> buildShip("Battleship", 20, 10));
        upgradeShipBtn.setOnAction(e -> upgradeSelectedShip());
        exploreBtn.setOnAction(e -> exploreSelectedPlanet());

        // Layout for the UI
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                statusLabel, fleetListView, buildFighterBtn, buildCruiserBtn,
                buildBattleshipBtn, upgradeShipBtn, gatherDilithiumBtn,
                planetSelector, exploreBtn, gameLog, resourceLabel
        );

        Scene scene = new Scene(layout, 500, 600);

        // Keyboard controls for game state changes
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));

        primaryStage.setTitle("Galactic Strategy"); // Set Title window (Primary stage)
        primaryStage.setScene(scene);               // Attach the scene to the primary stage
        primaryStage.show();                        // Displays primary stage (Opens window)

        // Game loop
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {  // 'now' is the current timestamp in nanoseconds
                updateGame();
            }
        };
        gameLoop.start();
    }

    // Handles keyboard input for game state management
    private void handleKeyPress(KeyCode key) {
        switch (key) {
            case ENTER:
                if (gameState == GameState.MENU) gameState = GameState.PLAYING;
                break;
            case P:
                if (gameState == GameState.PLAYING) gameState = GameState.PAUSED;
                else if (gameState == GameState.PAUSED) gameState = GameState.PLAYING;
                break;
            case ESCAPE:
                gameState = GameState.GAME_OVER;
                break;
        }
    }

    // Updates the game based on the current game state.
    private void updateGame() {
        // Use the switch statement to handle different game states
        switch (gameState) {
            case MENU:
                // In the MENU state, display a prompt for the player
                statusLabel.setText("In Menu... Press 'Enter' to play");
                break;
            case PLAYING:
                // in the PLAYING state, update game mechanics (e.g., move ships, process combat)
                statusLabel.setText("Game running... Manage Fleet & Explore");
                break;
            case PAUSED:
                // In the PAUSED state, show that the game is paused
                statusLabel.setText("Game Paused. Press 'P' to resume!");
                break;
            case GAME_OVER:
                // In the GAME_OVER state, display the game over message
                statusLabel.setText("Game Over! Press 'ESC' to exit");
                break;
        }
    }

    /*
     * Builds a new shp asynchronously
     * @param shipType The Type of ship to build
     */
    private void buildShip(String shipType, int mineralsCost, int energyCost) {
        if (inventory == null || !(inventory.useResource("Minerals", mineralsCost) && !inventory.useResource("Energy", energyCost))) {
            statusLabel.setText("Not enough resources to build " + shipType);
            return;
        }
        statusLabel.setText("Building " + shipType + "...");
        shipyard.buildShip(shipType);

        // Delayed fleet update after ship construction (simulates async build)
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(this::updateFleetDisplay, 3, TimeUnit.SECONDS);
        executor.shutdown();
    }

    private void upgradeSelectedShip() {
        String selectedShip = fleetListView.getSelectionModel().getSelectedItem();
        if (selectedShip == null || selectedShip.isEmpty()) {
            statusLabel.setText("Select a ship to upgrade!");
            return;
        }

        // Extract only the ship name before calling upgradeShip
        String shipName = selectedShip.split(" \\| ")[0]; // Extracts only the name
        shipyard.upgradeShip(shipName);
        updateFleetDisplay();
        statusLabel.setText(selectedShip + " upgraded!");
    }

    // Updates the fleet display UI
    private void updateFleetDisplay() {
        fleetListView.getItems().clear();
        if (shipyard == null || shipyard.getPlayerFleet() == null) return; // Prevent null error
        for (GalacticShip ship : shipyard.getPlayerFleet()) {
            fleetListView.getItems().add(ship.getName() + " | HP: " + ship.getHealth() + " | ATK: " + ship.getAttackPower());
        }
    }

    private void gatherDilithium() {
        resourceManagement.gatherResources(player, "Dilithium", inventory);
        resourceLabel.setText("Resources:\n" + inventory.displayResources()); // Update UI
    }

    // Handles exploring a selected planet
    private void exploreSelectedPlanet() {
        String planetName = planetSelector.getValue();
        Planet planet = new Planet(planetName); // Create a new Planet object

        gameLog.appendText("Exploring " + planetName + "...\n");
        explorationSystem.explorePlanet(player, planet, inventory);

        // Log fleet updates and resources
        updateFleetDisplay();
        resourceLabel.setText("Resources:\n" + inventory.displayResources());
    }

    // Stops the shipyard's background task before closing the game
    @Override
    public void stop() {
        shipyard.shutdown();
    }
}