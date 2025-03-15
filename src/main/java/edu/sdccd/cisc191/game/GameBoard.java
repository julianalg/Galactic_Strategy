package edu.sdccd.cisc191.game;

public class GameBoard {
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

    /**
     * Gets the resource cost to traverse a specific cell.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return The resource cost to traverse this cell.
     */
    public int getResourceCost(int row, int col) {
        return resourceCosts[row][col];
    }

    /**
     * Gets the entire planets array.
     *
     * @return A 2D array representing all planets on the board.
     */
    public int[][] getPlanets() {
        return planets;
    }

    /**
     * Gets the entire resource costs array.
     *
     * @return A 2D array representing all resource costs on the board.
     */
    public int[][] getResourceCosts() {
        return resourceCosts;
    }

    /**
     * Prints a visual representation of the game board with planets and costs.
     */
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

    public int getCellValue(int i, int j) {
        // Ensure i and j are within bounds
        if (i < 0 || i >= rows || j < 0 || j >= cols) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }

        // Pack the planet ID and resource cost into a single integer
        // Use the lower 16 bits for the planet ID and the upper 16 bits for the resource cost
        return (resourceCosts[i][j] << 16) | (planets[i][j] & 0xFFFF);
    }

    public int setCellValue(int i, int i1, int i2) {
        // Ensure i and j are within bounds
        i1 = getCellValue(i, i1);
        i2 = getCellValue(i, i2);
        int j = 0;
        if (i < 0 || i >= rows || j < 0 || j >= cols) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
        if (i1 < 0 || i1 >= rows || i2 < 0 || i2 >= cols) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
        if (i2 < 0 || i2 >= rows || i1 >= cols) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }

        // Pack the planet ID and resource cost into a single integer
        // Use the lower 16 bits for the planet ID and the upper 16 bits for the resource cost
        return (resourceCosts[i][j] << 16) | (planets[i][j] & 0xFFFF);
    }

    public int[][] getBoard() {
        int[][] board = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = planets[i][j];
            }
        } return board;
    }
}
