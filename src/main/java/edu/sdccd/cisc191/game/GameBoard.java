package edu.sdccd.cisc191.game;

public class GameBoard {
    // TODO: planets and resourceCosts could be final, since they are never changed after initialization
    private int[][] planets; // Represents planets in the galaxy (0 = empty, other values = planet IDs)
    private int[][] resourceCosts; // Represents resource cost to traverse each cell
    private final int rows = 5;
    private final int cols = 5;

    public GameBoard() {
        planets = new int[rows][cols];
        resourceCosts = new int[rows][cols];
    }

    /**
     * Initializes the game board with default values.
     * Planets are set to 0 (empty), and resource costs are set to default values.
     */
    public void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                planets[i][j] = 0; // No planet in this cell
                resourceCosts[i][j] = 1; // Default traversal cost
            }
        }

        placePlanet(0, 0, 1); // Earth

        placePlanet(2, 2, 2); // Mars

        placePlanet(4, 4, 3); // Jupiter
    }

    /**
     * Places a planet at the specified location.
     *
     * @param row      The row of the cell.
     * @param col      The column of the cell.
     * @param planetId The ID of the planet to place (e.g., 1, 2, 3...).
     */
    public void placePlanet(int row, int col, int planetId) {
        planets[row][col] = planetId;
    }

    /**
     * Sets the resource cost for traversing a specific cell.
     *
     * @param row  The row of the cell.
     * @param col  The column of the cell.
     * @param cost The resource cost to traverse this cell.
     */
    public void setResourceCost(int row, int col, int cost) {
        resourceCosts[row][col] = cost;
    }

    /**
     * Gets the ID of the planet at a specific location.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return The ID of the planet (or 0 if no planet is present).
     */
    public int getPlanetId(int row, int col) {
        return planets[row][col];
    }

    // TODO: write javadocs for the remaining methods within the class
    public int getResourceCost(int row, int col) {
        return resourceCosts[row][col];
    }

    public boolean inBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    // TODO: delete or implement unused displayBoard method
    public void displayBoard() {
        System.out.println("Planets:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(planets[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\nResource Costs:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(resourceCosts[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}