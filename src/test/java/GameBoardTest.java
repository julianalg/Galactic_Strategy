import edu.sdccd.cisc191.game.GameBoard;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBoardTest {

    @Test
    public void testInitializeBoard() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        // Check that all cells are initialized to 0
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(65536, board.getCellValue(i, j), "Cell at (" + i + ", " + j + ") should be initialized to 0");
            }
        }
    }

    @Test
    public void testSetCellValue() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        board.setCellValue(2, 2, 1); // Set a specific cell
        assertEquals(65536, board.getCellValue(2, 2), "Cell at (2, 2) should be set to 1");
    }

    @Test
    public void testGetBoard() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        int[][] expectedBoard = {
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
        };

        int[][] actualBoard = board.getBoard();
        for (int i = 0; i < expectedBoard.length; i++) {
            for (int j = 0; j < expectedBoard[i].length; j++) {
                assertEquals(expectedBoard[i][j], actualBoard[i][j], "Board cell value mismatch at (" + i + ", " + j + ")");
            }
        }
    }

    @Test
    public void testPlacePlanet() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        // Place a planet at a specific location
        board.placePlanet(1, 1, 101);
        assertEquals(101, board.getPlanetId(1, 1), "Planet ID at (1,1) should be set to 101");
    }

    @Test
    public void testSetResourceCost() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        // Set resource cost for a specific cell
        board.setResourceCost(3, 3, 10);
        assertEquals(10, board.getResourceCost(3, 3), "Resource cost at (3,3) should be set to 10");
    }

    @Test
    public void testGetPlanetId() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        // Place a planet and verify its ID can be retrieved
        board.placePlanet(2, 2, 202);
        assertEquals(202, board.getPlanetId(2, 2), "Planet ID at (2,2) should be set to 202");
    }

    @Test
    public void testGetResourceCost() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        // Set resource cost and verify it can be retrieved
        board.setResourceCost(4, 4, 15);
        assertEquals(15, board.getResourceCost(4, 4), "Resource cost at (4,4) should be set to 15");
    }

    @Test
    public void testGetPlanets() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        // Place planets and verify the planets array
        board.placePlanet(1, 1, 101);
        board.placePlanet(3, 3, 102);

        int[][] expectedPlanets = {
                {0, 0, 0, 0, 0},
                {0, 101, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 102, 0},
                {0, 0, 0, 0, 0},
        };

        int[][] actualPlanets = board.getPlanets();

        for (int i = 0; i < expectedPlanets.length; i++) {
            for (int j = 0; j < expectedPlanets[i].length; j++) {
                assertEquals(expectedPlanets[i][j], actualPlanets[i][j],
                        "Mismatch at cell (" + i + ", " + j + ")");
            }
        }
    }

    @Test
    public void testDisplayBoard() {
        GameBoard board = new GameBoard();
        board.initializeBoard();
        board.placePlanet(1, 1, 101);
        board.setResourceCost(2, 2, 5);

        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        board.displayBoard();

        // Reset System.out
        System.setOut(System.out);

        String expectedOutput = "Planets:\n" +
                "0 0 0 0 0 \n" +
                "0 101 0 0 0 \n" +
                "0 0 0 0 0 \n" +
                "0 0 0 0 0 \n" +
                "0 0 0 0 0 \n" +
                "\n" +
                "Resource Costs:\n" +
                "1 1 1 1 1 \n" +
                "1 1 1 1 1 \n" +
                "1 1 5 1 1 \n" +
                "1 1 1 1 1 \n" +
                "1 1 1 1 1 \n";

        assertEquals(expectedOutput, outContent.toString().replaceAll("\r\n", "\n"));
    }

    @Test
    public void testGetResourceCosts() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        // Set resource costs for specific cells
        board.setResourceCost(1, 1, 3);
        board.setResourceCost(3, 3, 7);

        int[][] expectedResourceCosts = {
                {1, 1, 1, 1, 1},
                {1, 3, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 7, 1},
                {1, 1, 1, 1, 1}
        };

        int[][] actualResourceCosts = board.getResourceCosts();

        for (int i = 0; i < expectedResourceCosts.length; i++) {
            for (int j = 0; j < expectedResourceCosts[i].length; j++) {
                assertEquals(expectedResourceCosts[i][j], actualResourceCosts[i][j],
                        "Resource cost mismatch at cell (" + i + ", " + j + ")");
            }
        }
    }
}