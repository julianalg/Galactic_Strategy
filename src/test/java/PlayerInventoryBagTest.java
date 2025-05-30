import edu.sdccd.cisc191.game.PlayerInventoryBag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerInventoryBagTest {

    @Test
    void testAddAndRemoveItem() {
        PlayerInventoryBag<String> bag = new PlayerInventoryBag<>();
        assertEquals(0, bag.getItemCount());

        bag.addItem("Dilithium");
        assertEquals(1, bag.getItemCount());
        assertTrue(bag.getItems().contains("Dilithium"));

        bag.addItem("Minerals");
        assertEquals(2, bag.getItemCount());
        assertTrue(bag.getItems().contains("Minerals"));

        assertTrue(bag.removeItem("Dilithium"));
        assertEquals(1, bag.getItemCount());
        assertFalse(bag.getItems().contains("Dilithium"));
    }
}
