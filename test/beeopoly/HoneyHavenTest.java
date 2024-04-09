package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HoneyHavenTest {

	HoneyHaven haven;
	String validNameMid,  invalidNameMin;
	Player player;
	@BeforeEach
	void setUp() throws Exception {
		
		validNameMid = "a".repeat(15);
		invalidNameMin = "a".repeat(2);
		
		player = new Player(validNameMid);
		haven = new HoneyHaven(validNameMid);
	}

	@Test
	void testHoneyHaven() {
		haven = new HoneyHaven(validNameMid);
		assertEquals(validNameMid, haven.getName());
		
	}
	@Test
	void testHoneyHavenInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, ()->{
			haven = new HoneyHaven(invalidNameMin);
		});assertEquals("Name must be 3-35 chars long", exp.getMessage());
	}
	@Test
	void testPassHoneyHaven() {
		//Check player honey (Starting value)
		assertEquals(500, player.getHoney());
		//Update player honey by 50
		player.updateHoney(50);
		assertEquals(550, player.getHoney());
		
	}

}
