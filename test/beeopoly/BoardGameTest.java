package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardGameTest {

	Player player;
	Garden garden;
	Field field;
	String name;
	int validValueMin, validValue, invalidValue;
	List<Player> activePlayers;
	List<Player> playerRank;
	ArrayList<Garden> gardens = new ArrayList<Garden>();

	@BeforeEach
	void setUp() throws Exception {
		field = new Field("Pollen Meadow");
		name = "Wild Rose Retreat";
		validValueMin = 0;
		validValue = 20;
		invalidValue = -1;

		activePlayers = new ArrayList<>();
		playerRank = new ArrayList<>();
		gardens = new ArrayList<Garden>();

		garden = new Garden(name, field, validValue, validValue);
		player = new Player("Test Name");
	}

	@Test
	void testRemovePlayer() {
		// Add & Check player is in the ArrayList
		activePlayers.add(player);
		assertTrue(activePlayers.contains(player));
		// remove the player, check if he has been removed
		activePlayers.remove(player);
		assertFalse(activePlayers.contains(player));
		// add the player to the rank
		playerRank.add(player);
		assertTrue(playerRank.contains(player));

	}

	@Test
	void testResetGardenResources() {
		// Add & Check player is a garden owner
		garden.setOwner(player);
		gardens.add(garden);
		assertEquals(player, garden.getOwner());
		
		for (Garden garden : gardens) {
			if (garden.getOwner() == player) {
				garden.setOwner(null);
				garden.setHives(0);
				garden.setApiary(0);
			}
		}
		
		for (Garden garden : gardens) {
			if (garden.getOwner() == player) {
                assertNull(garden.getOwner());
                assertEquals(0, garden.getHives());
                assertEquals(0, garden.getApiary());
            }
		}
	}

	@Test
	void testPlayerNumber() {

		garden.setHives(invalidValue);
		assertEquals(invalidValue, garden.getHives());

	}

}
