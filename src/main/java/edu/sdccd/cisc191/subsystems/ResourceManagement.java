package edu.sdccd.cisc191.subsystems;

import edu.sdccd.cisc191.game.Player;
import edu.sdccd.cisc191.game.Resource;
import edu.sdccd.cisc191.game.PlayerInventory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceManagement {
    private final Lock lock = new ReentrantLock();

    public void gatherResources(Player player, String resourceName, PlayerInventory inventory) {
        lock.lock();
        try {
           int collectedAmount = (int) (Math.random() * 10 + 5); // Random between 5-15
            inventory.addResource(resourceName, collectedAmount);
            System.out.println(player.getName() + " collcted " + collectedAmount + " " + resourceName + "!");
        } finally {
            lock.unlock();
        }
    }
}