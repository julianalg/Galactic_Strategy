package edu.sdccd.cisc191.subsystems;

import edu.sdccd.cisc191.game.Player;
import edu.sdccd.cisc191.game.Resource;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TradeSystem {
    private final Lock lock = new ReentrantLock();

    public void tradeResources(Player player1, Player player2, Resource resource) {
        lock.lock();
        try {
            System.out.println(player1.getName() + " is trading " + resource.getName() + " with " + player2.getName());
            // Simulate trade logic
            Thread.sleep(100); // Simulate time taken for trading
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}