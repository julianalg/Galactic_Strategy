package edu.sdccd.cisc191;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Demonstrates usage of Lambda expressions and Stream API.
 * This class is in the edu.sdccd.cisc191 package, which can be exported in module-info.java.
 */
public class FleetAnalyzer {

    public static void main(String[] args) {
        List<String> shipNames = Arrays.asList("Enterprise", "Voyager", "Defiant", "Discovery", "Reliant");

        // Lambda: Filter ships whose names start with 'D'
        Predicate<String> startsWithD = name -> name.startsWith("D");

        // Stream API: Filter and collect
        List<String> dShips = shipNames.stream()
                .filter(startsWithD)
                .collect(Collectors.toList());

        // Lambda: Print each ship name
        dShips.forEach(name -> System.out.println("Ship: " + name));

        // Stream API with Lambda: Count ships with length > 7
        long longNames = shipNames.stream()
                .filter(name -> name.length() > 7)
                .count();

        System.out.println("Number of ships with names longer than 7 characters: " + longNames);
    }
}
