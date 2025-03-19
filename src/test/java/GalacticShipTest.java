import edu.sdccd.cisc191.game.GalacticShip;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testCombatAbility() {
        // Initial state verification

        GalacticShip ship = new GalacticShip("USS Enterprise", 100, 20);
        assertTrue(ship.getCombatAbilities().isEmpty(),
                "New ship should have no abilities");

        // Test adding first ability
        ship.addCombatAbility(GalacticShip.CombatAbility.LASER_CANNON);
        assertTrue(ship.hasCombatAbility(GalacticShip.CombatAbility.LASER_CANNON),
                "Ship should have laser cannon after adding");
        assertEquals(1, ship.getCombatAbilities().size(),
                "Abilities list size should match added abilities");

        // Test adding second ability
        ship.addCombatAbility(GalacticShip.CombatAbility.SHIELD_GENERATOR);
        assertTrue(ship.hasCombatAbility(GalacticShip.CombatAbility.SHIELD_GENERATOR),
                "Ship should have shield generator after adding");
        assertEquals(2, ship.getCombatAbilities().size(),
                "Abilities list should contain both added abilities");

        // Test duplicate prevention
        ship.addCombatAbility(GalacticShip.CombatAbility.LASER_CANNON);
        assertEquals(2, ship.getCombatAbilities().size(),
                "Should prevent duplicate ability entries");

        // Test ability list contents
        List<GalacticShip.CombatAbility> expectedAbilities = List.of(
                GalacticShip.CombatAbility.LASER_CANNON,
                GalacticShip.CombatAbility.SHIELD_GENERATOR
        );
        assertTrue(ship.getCombatAbilities().containsAll(expectedAbilities),
                "Should contain all added abilities");

        // Test removing ability
        ship.removeCombatAbility(GalacticShip.CombatAbility.LASER_CANNON);
        assertFalse(ship.hasCombatAbility(GalacticShip.CombatAbility.LASER_CANNON),
                "Ship should no longer have laser cannon after removal");
        assertEquals(1, ship.getCombatAbilities().size(),
                "Abilities list should decrease after removal");
        assertTrue(ship.hasCombatAbility(GalacticShip.CombatAbility.SHIELD_GENERATOR),
                "Shield generator should remain after laser removal");

        // Test removing non-existent ability (edge case)
        ship.removeCombatAbility(GalacticShip.CombatAbility.CLOAKING_DEVICE);
        assertEquals(1, ship.getCombatAbilities().size(),
                "Removing non-existent ability should not affect list");
    }
}