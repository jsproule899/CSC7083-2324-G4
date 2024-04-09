package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
	
	String validNameMin, validNameMid, validNameMax, invalidNameMin, invalidNameMax;
	Player player;
	int honey, position;

	@BeforeEach
	void setUp() throws Exception {
		
		validNameMin = "a".repeat(3);
		validNameMid = "a".repeat(7);
		validNameMax = "a".repeat(15);
		invalidNameMin = "a".repeat(2);
		invalidNameMax = "a".repeat(16);
		
		honey = 500;
		position = 0;
		
		player = new Player(validNameMid);
	}
	

	@Test
	void testPlayerConstructor() {
		player = new Player(validNameMid);
		assertEquals(validNameMid, player.getName());
		assertEquals(honey, player.getHoney());
		assertEquals(position, player.getPosition());
	}

	@Test
	void testPlayerConstructorInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, ()->{
			player = new Player(invalidNameMin);
		}); assertEquals("Name must be 3-15 chars", exp.getMessage());
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
		assertEquals("Name must be 3-15 chars", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, ()->{
			player.setName(invalidNameMax);
		});
		assertEquals("Name must be 3-15 chars", exp.getMessage());
	}


}
