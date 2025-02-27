import edu.sdccd.cisc191.game.GameBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBoardTest {

    @Test
    public void testInitializeBoard() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        // Check that all cells are initialized to 0
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(0, board.getCellValue(i, j), "Cell at (" + i + ", " + j + ") should be initialized to 0");
            }
        }
    }

    @Test
    public void testSetCellValue() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        board.setCellValue(2, 2, 1); // Set a specific cell
        assertEquals(1, board.getCellValue(2, 2), "Cell at (2, 2) should be set to 1");
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
}