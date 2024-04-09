package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {

	String validNameMin, validNameMid, validNameMax, invalidNameMin, invalidNameMax;
	Player player;
	int honey, position, jarsSml, jarsLrg, roll, places;

	@BeforeEach
	void setUp() throws Exception {

		validNameMin = "a".repeat(3);
		validNameMid = "a".repeat(7);
		validNameMax = "a".repeat(15);
		invalidNameMin = "a".repeat(2);
		invalidNameMax = "a".repeat(16);

		honey = 500;
		position = 0;
		jarsSml = 20;
		jarsLrg = 200;

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
		Exception exp = assertThrows(IllegalArgumentException.class, () -> {
			player = new Player(invalidNameMin);
		});
		assertEquals("Name must be 3-15 chars", exp.getMessage());
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
		IllegalArgumentException exp = assertThrows(IllegalArgumentException.class, () -> {
			player.setName(invalidNameMin);
		});
		assertEquals("Name must be 3-15 chars", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, () -> {
			player.setName(invalidNameMax);
		});
		assertEquals("Name must be 3-15 chars", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, () -> {
			player.setName(null);
		});
		assertEquals("Name cannot be null", exp.getMessage());
	}

	@Test
	void testUpdateHoney() {
		player.updateHoney(jarsSml);
		// Should equal to honey (500) + jar amount sml (20)
		assertEquals(520, player.getHoney());
		// Should equal to current (520) + jar amount lrg (200)
		player.updateHoney(jarsLrg);
		assertEquals(720, player.getHoney());
	}

	@Test
	void testRollDice() {
		roll = player.rollDice();

		assertTrue(roll > 0 && roll < 13);
		assertTrue(roll > 0 && roll < 13);
		assertTrue(roll > 0 && roll < 13);
	}

	@Test
	void testMove() {
		places = 10;
		player.move(places);
		assertEquals(10, player.getPosition());
		places = 5;
		player.move(places);
		assertEquals(3, player.getPosition());
		places = 11;
		player.move(places);
		assertEquals(2, player.getPosition());
	}
}
