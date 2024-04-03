package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardGameTest {
	
	Player player;
	List<Player> activePlayers;
    List<Player> playerRank;

	@BeforeEach
	void setUp() throws Exception {

		player = new Player("Test Name");
		activePlayers = new ArrayList<>();
        playerRank = new ArrayList<>();
        activePlayers.add(player);
        
	}


	@Test
	void testRemovePlayer() {
		
		//Check player is in the ArrayList
		assertTrue(activePlayers.contains(player));
		//remove the player, check if he has been removed
		activePlayers.remove(player);
		assertFalse(activePlayers.contains(player));
		// add the player to the rank
		playerRank.add(player);
		assertTrue(playerRank.contains(player));
		
	}


}
