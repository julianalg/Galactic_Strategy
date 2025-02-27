import edu.sdccd.cisc191.game.GalacticShip;
import edu.sdccd.cisc191.game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    @Test
    public void testAddShip() {
        Player player = new Player("Test Player");
        GalacticShip ship1 = new GalacticShip("Ship One", 100, 20);
        GalacticShip ship2 = new GalacticShip("Ship Two", 80, 15);

        player.addShip(ship1);
        player.addShip(ship2);

        assertEquals(2, player.getFleet().size(), "Player should have 2 ships in the fleet");
    }

    @Test
    public void testGetTotalFleetHealth() {
        Player player = new Player("Test Player");
        GalacticShip ship1 = new GalacticShip("Ship One", 100, 20);
        GalacticShip ship2 = new GalacticShip("Ship Two", 80, 15);

        player.addShip(ship1);
        player.addShip(ship2);

        assertEquals(180, player.getTotalFleetHealth(), "Total fleet health should be 180");
    }

    @Test
    public void testGetName() {
        Player player = new Player("Test Player");
        assertEquals("Test Player", player.getName(), "Player's name should be 'Test Player'");
    }
}