package edu.sdccd.cisc191.subsystems;

import edu.sdccd.cisc191.game.GalacticShip;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

    /*
     * Features added:
     * Turn-based attack sequence
     * Attack power variation (randomized damage)
     * UI logs for attacks and destruction
     * Game Over condition if player's ship is destroyed
     */

// Handles combat between two ships in a turn-based system
public class CombatSystem {
    private final Lock lock = new ReentrantLock();
    private final Random random = new Random();

    /*
     * Engages combat between two ships with turn-based mechanics
     * @param playerShip The player's ship
     * @param enemyShip The enemy ship
     */

    public void engageCombat(GalacticShip playerShip, GalacticShip enemyShip) {
        lock.lock();
        try {
            System.out.println("Combat Started: " + playerShip.getName() + " vs. " + enemyShip.getName());

            while (!playerShip.isDestroyed() && !enemyShip.isDestroyed()) {
                // Player Attacks
                int playerAttack = playerShip.getAttackPower() + random.nextInt(5); // Slight damage variation
                enemyShip.takeDamage(playerAttack);
                System.out.println(playerShip.getName() + " attacks! " + enemyShip.getName() + " takes " + playerAttack + " damage. ");

                if (enemyShip.isDestroyed()) {
                    System.out.println(enemyShip.getName() + " has been destroyed!");
                    break;
                }

                // Enemy Attacks
                int enemyAttack = enemyShip.getAttackPower() + random.nextInt(5);
                playerShip.takeDamage(enemyAttack);
                System.out.println(enemyShip.getName() + " has been destroyed! GAME OVER!");
            }

            // Pause between turns to simulate real-time combat
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();

        }
    }
}