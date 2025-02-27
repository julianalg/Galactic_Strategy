package edu.sdccd.cisc191.subsystems;

import edu.sdccd.cisc191.game.Player;
import edu.sdccd.cisc191.game.Resource;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceManagement {
    private final Lock lock = new ReentrantLock();

    public void gatherResources(Player player, Resource resource) {
        lock.lock();
        try {
            System.out.println(player.getName() + " is gathering resources from " + resource.getName());
            // Simulate resource gathering logic
            Thread.sleep(100); // Simulate time taken for gathering
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}