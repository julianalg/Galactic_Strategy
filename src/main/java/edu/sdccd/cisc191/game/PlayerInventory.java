package edu.sdccd.cisc191.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// Manages player's inventory of resources
public class PlayerInventory implements Serializable {
    private Map<String, Resource> resources;

    public PlayerInventory() {
        resources = new HashMap<>();
        resources.put("Fuel", new Resource("Dilithium"));
        resources.put("Minerals", new Resource("Fuel"));
        resources.put("Minerals", new Resource("Minerals"));
        resources.put("Energy", new Resource("Energy"));
        resources.put("Credits", new Resource("Credits"));
    }

    public void addResource(String type, int amount) {
        if (resources.containsKey(type)) {
            resources.get(type).addAmount(amount);
        } else {
            resources.put(type, new Resource(type));
        }
    }

    public boolean useResource(String type, int amount) {
        return resources.containsKey(type) && resources.get(type).useAmount(amount);
    }

    public int getResourceAmount(String type) {
        if (resources.containsKey(type)) {
            return resources.get(type).getAmount();
        }
        return resources.getOrDefault(type, new Resource(type)).getAmount();
    }

    public String displayResources() {
        StringBuilder sb = new StringBuilder();
        for (Resource r : resources.values()) {
            sb.append(r.toString()).append("\n");
        }
        return sb.toString();
    }
}
