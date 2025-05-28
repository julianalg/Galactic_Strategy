package edu.sdccd.cisc191.game;

public class PlayerMovementManager {
    private final Player player;
    private final GameBoard board;
    private final PlayerInventory inventory;


    private int row = 0;
    private int col = 0;

    public PlayerMovementManager (Player player, GameBoard board, PlayerInventory inventory) {
        this.player = player;
        this.board = board;
        this.inventory = inventory;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getCurrentPlanetName() {
        int planetId = board.getPlanetId(row, col);
        return getPlanetNameById(planetId);

    }

    public boolean move(String direction) {
        int newRow = row;
        int newCol = col;

        switch (direction.toLowerCase()) {
            case "up" -> newRow--;
            case "down" -> newRow++;
            case "left" -> newCol--;
            case "right" -> newCol++;
            default -> {
                System.out.println("Invalid direction!");
                return false;
            }
        }

        if (!board.inBounds(newRow, newCol)) {
            System.out.println("Out of bounds!");
            return false;
        }

        int fuelCost = board.getResourceCost(newRow, newCol);
        if (!inventory.useResource("Fuel", fuelCost)) {
            System.out.println("Not enough fuel to move!");
            return false;
        }

        row = newRow;
        col = newCol;

        String planet = getPlanetNameById(board.getPlanetId(row, col));
        if (planet != null) {
            System.out.println(player.getName() + " arrived at " + planet);
        } else {
            System.out.println(player.getName() + " moved to empty space. ");
        }

        return true;
    }

    public boolean isAtHomePlanet () {
        return board.getPlanetId(row, col) == 1; // Earth = ID 1
    }

    private String getPlanetNameById(int id) {
        return switch (id) {
            case 1 -> "Earth";
            case 2 -> "Mars";
            case 3 -> "Jupiter";
            case 4 -> "Saturn";
            default -> null;
        };
    }
}