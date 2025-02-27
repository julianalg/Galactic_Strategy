import edu.sdccd.cisc191.game.Resource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.sdccd.cisc191.subsystems.TradeSystem;
import edu.sdccd.cisc191.subsystems.ResourceManagement;
import edu.sdccd.cisc191.subsystems.ExplorationSystem;
import edu.sdccd.cisc191.subsystems.CombatSystem;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import edu.sdccd.cisc191.game.GalacticShip;
import edu.sdccd.cisc191.game.Player;
import edu.sdccd.cisc191.game.Planet;

import java.util.concurrent.TimeUnit;

class SubsystemsConcurrencyTest {

    @Test
    void testConcurrentSubsystemOperations() throws InterruptedException {
        CombatSystem combatSystem = new CombatSystem();
        ExplorationSystem explorationSystem = new ExplorationSystem();
        ResourceManagement resourceManagement = new ResourceManagement();
        TradeSystem tradeSystem = new TradeSystem();

        GalacticShip ship1 = new GalacticShip("Enterprise");
        GalacticShip ship2 = new GalacticShip("Voyager");
        Player player1 = new Player("Kirk");
        Player player2 = new Player("Picard");
        Planet planet = new Planet("Mars");
        Resource dilithium = new Resource("Dilithium");

        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Submit concurrent tasks
        executor.submit(() -> combatSystem.engageCombat(ship1, ship2));
        executor.submit(() -> explorationSystem.explorePlanet(player1, planet));
        executor.submit(() -> resourceManagement.gatherResources(player2, dilithium));
        executor.submit(() -> tradeSystem.tradeResources(player1, player2, dilithium));

        // Shutdown the executor and wait for all tasks to complete
        executor.shutdown();
        boolean tasksFinished = executor.awaitTermination(5, TimeUnit.SECONDS);

        assertTrue(tasksFinished, "All concurrent tasks should complete within the timeout period");
    }

    @Test
    void testCombatSystemConcurrency() throws InterruptedException {
        CombatSystem combatSystem = new CombatSystem();
        GalacticShip ship1 = new GalacticShip("Enterprise");
        GalacticShip ship2 = new GalacticShip("Voyager");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit multiple combat engagements concurrently
        executor.submit(() -> combatSystem.engageCombat(ship1, ship2));
        executor.submit(() -> combatSystem.engageCombat(ship2, ship1));

        executor.shutdown();
        boolean tasksFinished = executor.awaitTermination(3, TimeUnit.SECONDS);

        assertTrue(tasksFinished, "Combat system should handle concurrent engagements");
    }

    @Test
    void testExplorationSystemConcurrency() throws InterruptedException {
        ExplorationSystem explorationSystem = new ExplorationSystem();
        Player player1 = new Player("Kirk");
        Player player2 = new Player("Picard");
        Planet planet1 = new Planet("Mars");
        Planet planet2 = new Planet("Venus");

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit multiple exploration tasks concurrently
        executor.submit(() -> explorationSystem.explorePlanet(player1, planet1));
        executor.submit(() -> explorationSystem.explorePlanet(player2, planet2));

        executor.shutdown();
        boolean tasksFinished = executor.awaitTermination(3, TimeUnit.SECONDS);

        assertTrue(tasksFinished, "Exploration system should handle concurrent explorations");
    }

    // Additional tests for ResourceManagement and TradeSystem can be added here
    // following a similar pattern to the above tests.
}
