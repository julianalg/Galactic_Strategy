package edu.sdccd.cisc191.game;

import java.util.*;

/**
 * A generic bag-style inventory for a player, allowing storage and management of any item type.
 * @param <T> The type of item stored in the inventory bag.
 */
public class PlayerInventoryBag<T> {
    private final List<T> items;

    public PlayerInventoryBag() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the bag.
     * @param item The item to add.
     */
    public void addItem(T item) {
        items.add(item);
    }

    /**
     * Removes an item from the bag.
     * @param item The item to remove.
     * @return true if the item was removed, false otherwise.
     */
    public boolean removeItem(T item) {
        return items.remove(item);
    }

    /**
     * Returns a list of all items in the bag.
     * @return Unmodifiable list of items.
     */
    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Returns the number of items in the bag.
     * @return The item count.
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * Prints all items in the bag.
     */
    public void printInventory() {
        System.out.println("Inventory Contents:");
        for (T item : items) {
            System.out.println("- " + item);
        }
    }
}
