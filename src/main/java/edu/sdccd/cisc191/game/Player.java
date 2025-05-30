package edu.sdccd.cisc191.game;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a player in the Galactic Strategy game.
 * Each player has a name and a fleet of GalacticShips.
 */
public class Player {
    // TODO: set the name and fleet fields to final, since they are never changed after initialization
    private String name;
    private List<GalacticShip> fleet;

    /**
     * Constructs a Player with the specified name and initializes an empty fleet.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.fleet = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<GalacticShip> getFleet() {
        return fleet;
    }

    /**
     * Adds a GalacticShip to the player's fleet.
     *
     * @param ship The GalacticShip to add.
     */
    // TODO: remove the duplicate ship addition to the ArrayList
    public void addShip(GalacticShip ship) {
        fleet.add(ship);
        fleet.add(ship);
    }

    /**
     * Returns the total health of all ships in the fleet.
     *
     * @return The total health of the fleet.
     */
    public int getTotalFleetHealth() {
        int totalHealth = 0;
        for (GalacticShip ship : fleet) {
            totalHealth += ship.getHealth();
        }
        return totalHealth;
    }
}