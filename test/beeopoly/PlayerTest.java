package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
	
	String validNameMin, validNameMid, validNameMax, invalidNameMin, invalidNameMax;
	Player player;

	@BeforeEach
	void setUp() throws Exception {
		
		validNameMin = "a".repeat(3);
		validNameMid = "a".repeat(7);
		validNameMax = "a".repeat(15);
		invalidNameMin = "a".repeat(2);
		invalidNameMax = "a".repeat(16);
		
		player = new Player(validNameMid);
	}
	

	@Test
	void testPlayerConstructor() {
		player = new Player(validNameMid);
		assertEquals(validNameMid, player.getName());
	}

	@Test
	void testPlayerConstructorInvalid() {
		assertThrows(IllegalArgumentException.class, ()->{
			player = new Player(invalidNameMin);
		});
	}
	
	@Test
	void testSetName() {
		player.setName(validNameMin);
		assertEquals(validNameMin, player.getName());
		player.setName(validNameMid);
		assertEquals(validNameMid, player.getName());
		player.setName(validNameMax);
		assertEquals(validNameMax, player.getName());
	}
	
	@Test
	void testSetNameInvalid() {
		IllegalArgumentException exp = assertThrows(IllegalArgumentException.class, ()->{
			player.setName(invalidNameMin);
		});
		assertEquals("Name length is invalid", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, ()->{
			player.setName(invalidNameMax);
		});
		assertEquals("Name length is invalid", exp.getMessage());
	}


}
