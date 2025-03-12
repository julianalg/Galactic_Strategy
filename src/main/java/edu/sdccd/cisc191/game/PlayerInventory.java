package edu.sdccd.cisc191.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// Manages player's inventory of resources
public class PlayerInventory implements Serializable {
    private Map<String, Resource> resources;

    public PlayerInventory() {
        resources = new HashMap<>();
        resources.put("Fuel", new Resource("Dilithium", 10));
        resources.put("Minerals", new Resource("Fuel", 50));
        resources.put("Minerals", new Resource("Minerals", 30));
        resources.put("Energy", new Resource("Energy", 20));
        resources.put("Credits", new Resource("Credits", 100));
    }

    public void addResource(String type, int amount) {
        if (resources.containsKey(type)) {
            resources.get(type).addAmount(amount);
        } else {
            resources.put(type, new Resource(type, amount));
        }
    }

    public boolean useResource(String type, int amount) {
        return resources.containsKey(type) && resources.get(type).useAmount(amount);
    }

    public int getResourceAmount(String type) {
        return resources.getOrDefault(type, new Resource(type, 0)).getAmount();
    }

    public String displayResources() {
        StringBuilder sb = new StringBuilder();
        for (Resource r : resources.values()) {
            sb.append(r.toString()).append("\n");
        }
        return sb.toString();
    }
}
