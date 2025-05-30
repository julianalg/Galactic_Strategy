package edu.sdccd.cisc191.subsystems;

import edu.sdccd.cisc191.game.Player;
import edu.sdccd.cisc191.game.Resource;
import edu.sdccd.cisc191.game.PlayerInventory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceManagement {
    private final Lock lock = new ReentrantLock();

    // TODO: clean up multiple different gatherResources methods
    public void gatherResources(Player player, String resourceName, PlayerInventory inventory) {
        lock.lock();
        try {
            int collectedAmount = (int) (Math.random() * 10 + 5); // Random between 5-15
            inventory.addResource(resourceName, collectedAmount);
            // TODO: replace System.out.println with logging this information to the in-game UI log
            System.out.println(player.getName() + " collected " + collectedAmount + " " + resourceName + "!");
        } finally {
            lock.unlock();
        }
    }

    public void gatherResources(Player player2, Resource dilithium) {
        lock.lock();
        try {
            int collectedAmount = (int) (Math.random() * 10 + 5);
            // TODO: replace System.out.println with logging this information to the in-game UI log
            System.out.println(player2.getName() + " collected " + collectedAmount + " " + player2.getName() + "!");
        } finally {
            lock.unlock();
        }
    }
}