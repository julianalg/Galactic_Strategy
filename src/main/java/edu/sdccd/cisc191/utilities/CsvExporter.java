package edu.sdccd.cisc191.utilities;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;


import edu.sdccd.cisc191.game.GalacticShip;
import edu.sdccd.cisc191.game.Player;

public class CsvExporter {
    public static void exportFleetToCsv(Player player, String fileName) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            String[] header = {"Player Name", "Ship Name", "Ship Health", "Ship Attack Power"};
            writer.writeNext(header);

            for (GalacticShip ship : player.getFleet()) {
                String[] data = {
                        player.getName(),
                        ship.getName(),
                        String.valueOf(ship.getHealth()),
                        String.valueOf(ship.getAttackPower())
                };
                writer.writeNext(data);
            }
            System.out.println("CSV file created: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
