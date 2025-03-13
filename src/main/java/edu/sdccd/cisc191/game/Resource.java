package edu.sdccd.cisc191.game;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Resource implements Serializable {
    private static final long serialVersionUID = 1L; // Prevents serialization issues

    private String name;
    private int amount;
    private final Lock lock = new ReentrantLock();

    public Resource(String name) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    // Adds Resources in a thread-safe manner
    public void addAmount(int value) {
        lock.lock();
        try {
            this.amount += value;
            System.out.println("Added " + value + " " + ". New total: " + amount);
        } finally {
            lock.unlock();
        }
    }

    // Uses resources in a thread-safe manner
    public boolean useAmount(int value) {
        lock.lock();
        try {
            if (this.amount < value) {
                System.out.println("Not enough " + name + "! Need: " + value + ", Have: " + amount);
                return false;
            }
            this.amount -= value;
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return name + ": " + amount;
    }
}