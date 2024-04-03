package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GardenTest {
	
	int validValueMin, validValue, invalidValue;
	Field field;
	Garden garden;
	String name;

	@BeforeEach
	void setUp() throws Exception {
		field = new Field("Pollen Meadow");
		name = "Wild Rose Retreat";
		validValueMin = 0;
		validValue = 20;
		invalidValue = -1;
		
		garden = new Garden(name, field, validValue, validValue, validValue);
	}

	@Test
	void testGardenConstructor() {
		garden = new Garden(name, field, validValue, validValue, validValue);
		assertEquals("Wild Rose Retreat", garden.getName());
		//assertEquals("Pollen Meadow", garden.getField());
		assertEquals(20, garden.getTileCost());
		assertEquals(20, garden.getRent());
		assertEquals(20, garden.getBuildCost());
	}
	
	@Test
	void testGardenConstructorInvalid() {
		//add validation to parent class
		assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(null, field, validValueMin, validValue, invalidValue);
		});
		assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(null, field, validValueMin, validValue, invalidValue);
		});
	}

//	@Test
//	void testGetHives() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateHives() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateApiaries() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testBuildHive() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetApiary() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetOwner() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetField() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetTileCost() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetRent() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testPayRent() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetBuildCost() {
//		fail("Not yet implemented");
//	}

}
