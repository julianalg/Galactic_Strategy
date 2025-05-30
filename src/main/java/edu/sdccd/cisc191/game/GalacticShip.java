package edu.sdccd.cisc191.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a spaceship in the Galactic Strategy game.
 * Each ship has a name, health, attack power, and combat abilities, and can engage in combat.
 */
public class GalacticShip {
    // TODO: make field final, since they are never changed after initialization
    private String name;
    private int health;
    private int attackPower;
    // TODO: make field final, since they are never changed after initialization
    private List<CombatAbility> combatAbilities;

    /**
     * Enum representing different combat abilities a ship can have.
     */
    public enum CombatAbility {
        LASER_CANNON,
        SHIELD_GENERATOR,
        MISSILE_LAUNCHER,
        CLOAKING_DEVICE,
        REPAIR_DRONES
    }

    /**
     * Constructs a GalacticShip with the specified name, health, and attack power.
     *
     * @param name The name of the ship.
     * @param health The initial health of the ship.
     * @param attackPower The attack power of the ship.
     */
    public GalacticShip(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.combatAbilities = new ArrayList<>();
    }

    public GalacticShip(String enterprise) {
        this.health = 0;
        this.name = enterprise;
        this.combatAbilities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0; // health cannot go below 0
        }
    }

    public void attack(GalacticShip target) {
        target.takeDamage(this.attackPower);
    }

    public boolean isDestroyed() {
        return this.health <= 0;
    }

    /**
     * Adds a combat ability to the ship.
     *
     * @param ability The combat ability to add.
     */
    public void addCombatAbility(CombatAbility ability) {
        if (!combatAbilities.contains(ability)) {
            combatAbilities.add(ability);
        }
    }

    /**
     * Removes a combat ability from the ship.
     *
     * @param ability The combat ability to remove.
     */
    public void removeCombatAbility(CombatAbility ability) {
        combatAbilities.remove(ability);
    }

    /**
     * Checks if the ship has a specific combat ability.
     *
     * @param ability The combat ability to check for.
     * @return true if the ship has the ability, false otherwise.
     */
    public boolean hasCombatAbility(CombatAbility ability) {
        return combatAbilities.contains(ability);
    }

    /**
     * Gets a list of all combat abilities the ship has.
     *
     * @return A list of the ship's combat abilities.
     */
    public List<CombatAbility> getCombatAbilities() {
        return new ArrayList<>(combatAbilities);
    }
}