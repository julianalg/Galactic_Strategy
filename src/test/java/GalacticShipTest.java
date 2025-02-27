import edu.sdccd.cisc191.game.GalacticShip;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GalacticShipTest {

    @Test
    public void testAttack() {
        GalacticShip attacker = new GalacticShip("Attacker", 100, 20);
        GalacticShip target = new GalacticShip("Target", 50, 5);

        attacker.attack(target);
        assertEquals(30, target.getHealth(), "Target should have 30 health after being attacked");
    }

    @Test
    public void testTakeDamage() {
        GalacticShip ship = new GalacticShip("Test Ship", 100, 10);
        ship.takeDamage(30);
        assertEquals(70, ship.getHealth(), "Ship should have 70 health after taking 30 damage");

        ship.takeDamage(100);
        assertEquals(0, ship.getHealth(), "Ship health should not go below 0 after taking excess damage");
    }

    @Test
    public void testIsDestroyed() {
        GalacticShip ship = new GalacticShip("Destroyable", 50, 10);
        ship.takeDamage(50);
        assertTrue(ship.isDestroyed(), "Ship should be destroyed after taking 50 damage");
    }

    @Test
    public void testGetAttackPower() {
        GalacticShip ship = new GalacticShip("Test Ship", 100, 15);
        assertEquals(15, ship.getAttackPower(), "Ship's attack power should be 15");
    }

    @Test
    public void testGetName() {
        GalacticShip ship = new GalacticShip("Test Ship", 100, 10);
        assertEquals("Test Ship", ship.getName(), "Ship's name should be 'Test Ship'");
    }
}