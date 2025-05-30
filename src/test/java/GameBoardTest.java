import edu.sdccd.cisc191.game.GameBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    @Test
    void testInitializeBoard() {
        GameBoard board = new GameBoard();
        board.initializeBoard();

        // Earth
        assertEquals(1, board.getPlanetId(0, 0));
        // Mars
        assertEquals(2, board.getPlanetId(2, 2));
        // Jupiter
        assertEquals(3, board.getPlanetId(4, 4));

        // All other cells should be empty (0)
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (!((i == 0 && j == 0) || (i == 2 && j == 2) || (i == 4 && j == 4))) {
                    assertEquals(0, board.getPlanetId(i, j));
                }
                // Default resource cost is 1
                assertEquals(1, board.getResourceCost(i, j));
            }
        }
    }

    @Test
    void testPlacePlanetAndGetPlanetId() {
        GameBoard board = new GameBoard();
        board.placePlanet(1, 1, 5);
        assertEquals(5, board.getPlanetId(1, 1));
    }

    @Test
    void testSetAndGetResourceCost() {
        GameBoard board = new GameBoard();
        board.setResourceCost(3, 3, 7);
        assertEquals(7, board.getResourceCost(3, 3));
    }

    @Test
    void testInBounds() {
        GameBoard board = new GameBoard();
        assertTrue(board.inBounds(0, 0));
        assertTrue(board.inBounds(4, 4));
        assertFalse(board.inBounds(-1, 0));
        assertFalse(board.inBounds(0, 5));
        assertFalse(board.inBounds(5, 5));
    }
}
