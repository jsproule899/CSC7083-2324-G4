package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HoneyHavenTest {

	HoneyHaven haven;
	String name;
	Player player;
	@BeforeEach
	void setUp() throws Exception {
		
		name = "Honey Haven";
		
		player = new Player("Test player");
		haven = new HoneyHaven(name);
	}

	@Test
	void testHoneyHaven() {
		haven = new HoneyHaven(name);
		assertEquals(name, haven.getName());
		
	}
	@Test
	void testHoneyHavenInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, ()->{
			haven = new HoneyHaven(null);
		});assertEquals("Cannot be a null value", exp.getMessage());
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
