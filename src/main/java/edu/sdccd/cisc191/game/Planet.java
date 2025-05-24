package edu.sdccd.cisc191.game;

public class Planet {

    public final String mars;
    public final String earth;
    public final String jupiter;
    public final String andromeda;
    public final String neptune;

    // Existing 5-argument constructor
    public Planet(String mars, String jupiter, String earth, String andromeda, String neptune) {
        this.mars = mars;
        this.earth = earth;
        this.jupiter = jupiter;
        this.andromeda = andromeda;
        this.neptune = neptune;
    }

    // New single-argument constructor
    public Planet(String planetName) {
        this.mars = "Mars".equalsIgnoreCase(planetName) ? planetName : null;
        this.earth = "Earth".equalsIgnoreCase(planetName) ? planetName : null;
        this.jupiter = "Jupiter".equalsIgnoreCase(planetName) ? planetName : null;
        this.andromeda = "Andromeda".equalsIgnoreCase(planetName) ? planetName : null;
        this.neptune = "Neptune".equalsIgnoreCase(planetName) ? planetName : null;
    }

    public String getEarth() {
        return earth != null ? earth : "Earth";
    }

    public String getMars() {
        return mars != null ? mars : "Mars";
    }

    public String getJupiter() {
        return jupiter != null ? jupiter : "Jupiter";
    }

    public String getAndromeda() {
        return andromeda != null ? andromeda : "Andromeda";
    }

    public String getNeptune() {
        return neptune != null ? neptune : "Neptune";
    }

    public String getName() {
        if (mars != null) return mars;
        if (earth != null) return earth;
        if (jupiter != null) return jupiter;
        if (andromeda != null) return andromeda;
        if (neptune != null) return neptune;
        return "Unknown";
    }
}
