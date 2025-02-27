package edu.sdccd.cisc191.subsystems;

import edu.sdccd.cisc191.game.Planet;
import edu.sdccd.cisc191.game.Player;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExplorationSystem {
    private final Lock lock = new ReentrantLock();

    public void explorePlanet(Player player, Planet planet) {
        lock.lock();
        try {
            System.out.println(player.getName() + " is exploring " + planet.getName());
            // Simulate exploration logic
            Thread.sleep(100); // Simulate time taken for exploration
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}