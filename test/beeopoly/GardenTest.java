package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GardenTest {
	
	//test data
	int validValueMin, validValue, invalidValue;
	int validApiaryMin, validApiaryMax, invalidApiaryMin, invalidApiaryMax;
	int validHiveMin, validHiveMid, validHiveMax, invalidHiveMin, invalidHiveMax;
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
		//validApiaryMin = 0; 
		validApiaryMax =1;
		invalidApiaryMin = -1;
		invalidApiaryMax = 2;
		//validHiveMin = 0; 
		validHiveMid = 2; 
		validHiveMax = 3; 
		invalidHiveMin = -1; 
		invalidHiveMax = 4;
		
		
		garden = new Garden(name, field, validValue, validValue);
	}

	//Test Garden constructor
	@Test
	void testGardenConstructor() {
		garden = new Garden(name, field, validValue, validValue);
		//Test the abstract parent constructor
		assertEquals(name, garden.getName());
		//Garden constructor tests
		assertEquals(field, garden.getField());
		assertEquals(validValue, garden.getTileCost());
		assertEquals(validValue, garden.getRent());
		
	}
	
	//Test Garden constructor invalid
	@Test
	void testGardenConstructorInvalid() {
		//Test the abstract parent constructor
		Exception exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(null, field, validValueMin, validValue);
		});assertEquals("Cannot be a null value", exp.getMessage());
		//Garden constructor tests
		exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(name, null, validValue, validValue);
		});assertEquals("Cannot be a null value", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(name, field, invalidValue, validValue);
		});assertEquals("Value cannot be less than 0", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(name, field, validValue, invalidValue);
		});assertEquals("Value cannot be less than 0", exp.getMessage());

	}

	@Test
	void testSetHive() {
		garden.setHives(validHiveMin);
		assertEquals(validHiveMin, garden.getHives());
		garden.setHives(validHiveMid);
		assertEquals(validHiveMid, garden.getHives());
		garden.setHives(validHiveMax);
		assertEquals(validHiveMax, garden.getHives());
		garden.setHives(invalidHiveMax);
		assertEquals(invalidHiveMax, garden.getHives());
		
	}
	@Test
	void testSetHiveInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, () -> {
			System.out.println(validHiveMax);
			garden.setHives(-1);
			System.out.println(garden.getHives());
		});
		assertEquals("Hives cannot be set to less than 0", exp.getMessage());
	}

	@Test
	void testBuildHive() {
		garden.setHives(0);
		garden.buildHive();
		assertEquals(1, garden.getHives());
		garden.setHives(2);
		garden.buildHive();
		assertEquals(3, garden.getHives());
		
	}

	@Test
	void testBuildApiary() {
		garden.setApiary(0);
		garden.buildApiary();
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

	

}
