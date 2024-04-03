package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GardenTest {
	
	//test data
	int validValueMin, validValue, invalidValue;
	int hives;
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
		
		hives = 0;
		
		garden = new Garden(name, field, validValue, validValue, validValue);
	}

	//Test Garden constructor
	@Test
	void testGardenConstructor() {
		garden = new Garden(name, field, validValue, validValue, validValue);
		//Test the abstract parent constructor
		assertEquals(name, garden.getName());
		//Garden constructor tests
		assertEquals(field, garden.getField());
		assertEquals(validValue, garden.getTileCost());
		assertEquals(validValue, garden.getRent());
		assertEquals(validValue, garden.getBuildCost());
	}
	
	//Test Garden constructor invalid
	@Test
	void testGardenConstructorInvalid() {
		//Test the abstract parent constructor
		Exception exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(null, field, validValueMin, validValue, invalidValue);
		});assertEquals("Cannot be a null value", exp.getMessage());
		//Garden constructor tests
		exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(name, null, validValue, validValue, validValue);
		});assertEquals("Cannot be a null value", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(name, field, invalidValue, validValue, validValue);
		});assertEquals("Value cannot be less than 0", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(name, field, validValue, invalidValue, validValue);
		});assertEquals("Value cannot be less than 0", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(name, field, validValue, validValue, invalidValue);
		});assertEquals("Value cannot be less than 0", exp.getMessage());
	}


	@Test
	void testUpdateHives() {
		garden.setHives(0);
		garden.updateHives();
		assertEquals(1, garden.getHives());
		garden.setHives(2);
		garden.updateHives();
		assertEquals(3, garden.getHives());
		
	}

	@Test
	void testUpdateApiaries() {
		garden.setApiary(0);
		garden.updateApiaries();
		assertEquals(1, garden.getApiary());
	}


	//Do we need to test for min and max values?
	@Test
	void testGetField() {
		garden.setField(field);
		assertEquals(field, garden.getField());
	}
	
	@Test
	void testGetFieldInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, () -> {
			garden.setField(null);
		});
		assertEquals("Cannot be a null value", exp.getMessage());
	}

	@Test
	void testGetTileCost() {
		garden.setTileCost(validValueMin);
		assertEquals(0, garden.getTileCost());
		garden.setTileCost(validValue);
		assertEquals(20, garden.getTileCost());
		
	}
	
	@Test
	void testGetTileCostInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, () -> {
			garden.setTileCost(invalidValue);
		});assertEquals("Value cannot be less than 0", exp.getMessage());
		
	}

	@Test
	void testGetRent() {
		garden.setRent(validValueMin);
		assertEquals(0, garden.getRent());
		garden.setRent(validValue);
		assertEquals(20, garden.getRent());
	}
	
	@Test
	void testGetRentInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, () -> {
			garden.setRent(invalidValue);
		});assertEquals("Value cannot be less than 0", exp.getMessage());
		
	}
//	Methods need to be split to test 
//	@Test
//	void testPayRent() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGetBuildCost() {
		garden.setBuildCost(validValueMin);
		assertEquals(0, garden.getBuildCost());
		garden.setBuildCost(validValue);
		assertEquals(20, garden.getBuildCost());
	}
	
	@Test
	void testGetBuildCostInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, () -> {
			garden.setBuildCost(invalidValue);
		});assertEquals("Value cannot be less than 0", exp.getMessage());
	}

}
