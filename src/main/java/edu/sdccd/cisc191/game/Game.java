package edu.sdccd.cisc191.game;

import edu.sdccd.cisc191.subsystems.CombatSystem;
import edu.sdccd.cisc191.subsystems.ExplorationSystem;
import edu.sdccd.cisc191.subsystems.ResourceManagement;
// TODO: remove unused import
import edu.sdccd.cisc191.game.PlayerInventory;
import edu.sdccd.cisc191.utilities.GameUI;

import javafx.animation.AnimationTimer; // 1 Game loop that calls handle() method
import javafx.application.Application;  // 2 JavaFX application base class
import javafx.scene.Scene;              // 3 Container for all content in a scene graph
import javafx.scene.control.*;      // 4 IU control text display
import javafx.scene.input.KeyCode;      // 5 Provides key codes to handle keyboard input
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;              // 7 Top-level window

import javafx.scene.control.ListView;                   // 8
import javafx.scene.layout.VBox;                        // 9
import javafx.scene.control.Button;                     // 10

import java.util.concurrent.Executors;                  // 11
import java.util.concurrent.ScheduledExecutorService;   // 12
import java.util.concurrent.TimeUnit;                   // 13



// Main Game Class (Integrates JavaFX, Shipyard System, and Exploration System)
public class Game extends Application {
    // TODO: break Game into different classes for UI, game logic, and subsystems
    // TODO: create separate packages for game resources, like Planet, Player, PlayerInventory, etc.
    private Shipyard shipyard;
    private ExplorationSystem explorationSystem;
    private ResourceManagement resourceManagement;
    private Player player;
    private PlayerInventory inventory;
    private GameBoard gameBoard;
    private PlayerMovementManager movementManager;


    private ListView<String> fleetListView;     // IUO List for displaying player's fleet
    private Label statusLabel;                  // Status label
    private TextArea gameLog;
    private ComboBox<String> planetSelector;// Field to store the current state of the game
    private Label resourceLabel;
    private GameUI gameUI;
    private CombatSystem combatSystem;
    private Label locationLabel;

    private GameState gameState;

    public enum GameState {                     // Define an enumeration for different game states
        MENU, PLAYING, PAUSED, GAME_OVER
    }

    public static void main(String[] args) {    // Launch the JavaFX application
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) {// Main window
        // TODO: split this portion to load game into a separate method
        // Initialize game components
        shipyard = new Shipyard();
        explorationSystem = new ExplorationSystem();
        resourceManagement = new ResourceManagement();
        inventory = new PlayerInventory();
        player = new Player ("Captain");
        player.addShip(new GalacticShip("Starter Ship", 100, 20)); // Start with a ship
        locationLabel = new Label("Location: Earth"); // Default location
        gameBoard = new GameBoard();
        gameBoard.initializeBoard();
        movementManager = new PlayerMovementManager(player, gameBoard, inventory);

        gameState = GameState.MENU;

        // Create UI elements
        // TODO: separate these methods to load specific javafx components into separate components
        statusLabel = new Label("Welcome to Galactic Strategy! (Press ENTER to start)");
        fleetListView = new ListView<>();
        updateFleetDisplay();   // Load fleet data

        gameLog = new TextArea();
        gameLog.setEditable(false);
        // TODO: make this view larger in order to display more player information
        gameLog.setPrefHeight(150);

        resourceLabel = new Label("Resources:\n" + inventory.displayResources());

        // Shipyard UI Buttons
        // TODO: utilize JavaFX CSS styling to add more detail and differentiation between these buttons
        // TODO: display all buttons if the GameState is menu, paused, or gameover
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
        planetSelector.getItems().addAll("Mars", "Earth", "Jupiter", "Neptune", "Alpha Centauri", "Andromeda");
        planetSelector.setValue("Mars"); // Default selection

        // Assign button actions
        buildFighterBtn.setOnAction(e -> buildShip("Fighter", 10, 5));
        buildCruiserBtn.setOnAction(e -> buildShip("Cruiser", 15, 7));
        buildBattleshipBtn.setOnAction(e -> buildShip("Battleship", 20, 10));
        upgradeShipBtn.setOnAction(e -> upgradeSelectedShip());
        exploreBtn.setOnAction(e -> exploreSelectedPlanet());

        Button upBtn = new Button("↑");
        Button downBtn = new Button("↓");
        Button leftBtn = new Button("←");
        Button rightBtn = new Button("→");

        HBox moveRow1 = new HBox(10, upBtn);
        HBox moveRow2 = new HBox(10, leftBtn, downBtn, rightBtn);
        VBox movementControls = new VBox(5, moveRow1, moveRow2);

        // TODO: add UI component to display amount of minerals, energy of user within the javafx UI

        // Layout for the UI
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                statusLabel, locationLabel, fleetListView, buildFighterBtn, buildCruiserBtn,
                buildBattleshipBtn, upgradeShipBtn, gatherDilithiumBtn,
                planetSelector, exploreBtn, movementControls, gameLog, resourceLabel
        );

        Scene scene = new Scene(layout, 500, 600);

        // Keyboard controls for game state changes
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));


        primaryStage.setTitle("Galactic Strategy"); // Set Title window (Primary stage)
        primaryStage.setScene(scene);               // Attach the scene to the primary stage
        primaryStage.show();                        // Displays primary stage (Opens window)

        upBtn.setOnAction(e -> handleMove("up"));
        downBtn.setOnAction(e -> handleMove("down"));
        leftBtn.setOnAction(e -> handleMove("left"));
        rightBtn.setOnAction(e -> handleMove("right"));

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
    // TODO: remap buttons, ENTER and ESCAPE for start and ending respectively don't appear to work, at least on Mac
    private void handleKeyPress(KeyCode key) {
        switch (key) {
            case A:
                if (gameState == GameState.MENU) gameState = GameState.PLAYING;
                break;
            case P:
                if (gameState == GameState.PLAYING) gameState = GameState.PAUSED;
                else if (gameState == GameState.PAUSED) gameState = GameState.PLAYING;
                break;
            case E:
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

    private void handleMove(String direction) {
        boolean moved = movementManager.move(direction);
        if (moved) {
            int r = movementManager.getRow();
            int c = movementManager.getCol();
            String planet = getPlanetNameById(gameBoard.getPlanetId(r, c));
            locationLabel.setText("Location: (" + r + "," + c + ")");
            statusLabel.setText(planet.equals("Unknown") ? "Empty space" : "Arrived at " + planet);
            resourceLabel.setText("Resource:\n" + inventory.displayResources());
            gameUI.appendMessage("Moved " + direction + " to (" + r + "," + c + ")\n");
        } else {
            statusLabel.setText("Move failed (no fuel or out of bounds)");
        }
    }

    // TODO: delete this method to test combat that is never used
    private void runCombatExample() {
        GalacticShip playerShip = new GalacticShip("Enterprise", 100, 20);
        GalacticShip enemyShip = new GalacticShip("Klingon Raider", 80, 18);
        CombatSystem combatSystem = new CombatSystem();

        // Engage combat (logs will appear in the console as per CombatSystem)
        combatSystem.engageCombat(playerShip, enemyShip);

        // Optionally, display result in the UI if gameUI is available
        if (gameUI != null) {
            if (playerShip.isDestroyed()) {
                gameUI.appendMessage(playerShip.getName() + "was destroyed! GAME OVER!");
            } else if (enemyShip.isDestroyed()) {
                gameUI.appendMessage(playerShip.getName() + "was destroyed! Victory!");
            }
        }
    }

    // TODO: delete this method to load UI that was never used
    private void setupUI(Stage stage) {
        gameUI = new GameUI();
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 500, 600);
        stage.setScene(scene);
        stage.setTitle("Galactic Strategy");
        stage.show();

        gameUI.appendMessage("Welcome to Galactic Strategy!");
    }



    /*
     * Builds a new shp asynchronously
     * @param shipType The Type of ship to build
     */
    private void buildShip(String shipType, int mineralsCost, int energyCost) {
        System.out.println(mineralsCost + " " + energyCost + " " + shipType);
        if (inventory == null || !inventory.useResource("Minerals", mineralsCost) || !inventory.useResource("Energy", energyCost)) {
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
        int r = movementManager.getRow();
        int c = movementManager.getCol();
        int planetId = gameBoard.getPlanetId(r, c);

        if (planetId == 0) {
            statusLabel.setText("There is no planet here to explore");
            return;
        }

        String planetName = getPlanetNameById(planetId);
        Planet currentPlanet = new Planet(planetName);
        explorationSystem.explorePlanet(player, currentPlanet, inventory);

        statusLabel.setText("explored " + planetName + "!");
        gameLog.appendText("Explored " + planetName + " at (" + r + "," + c + ")\n");
        updateFleetDisplay();
        resourceLabel.setText("Resources:\n" + inventory.displayResources());
    }

    private String getPlanetNameById(int id) {
        return switch (id) {
            case 1 -> "Earth";
            case 2 -> "Mars";
            case 3 -> "Jupiter";
            case 4 -> "Saturn";
            default -> "Unknown";
        };
    }

    // Stops the shipyard's background task before closing the game
    @Override
    public void stop() {
        shipyard.shutdown();
    }
}