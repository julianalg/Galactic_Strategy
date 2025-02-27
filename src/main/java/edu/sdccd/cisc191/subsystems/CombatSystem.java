package edu.sdccd.cisc191.subsystems;

import edu.sdccd.cisc191.game.GalacticShip;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CombatSystem {
    private final Lock lock = new ReentrantLock();

    public void engageCombat(GalacticShip attacker, GalacticShip defender) {
        lock.lock();
        try {
            // Implement combat logic here
            System.out.println("Engaging combat between " + attacker.getName() + " and " + defender.getName());
            // Simulate combat
            Thread.sleep(100); // Simulate time taken for combat
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}