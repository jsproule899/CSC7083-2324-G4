package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GardenTest {
	
	//test data
	int validValueMin, validValue, invalidValue;
	int validApiaryMin, validApiaryMax, invalidApiaryMin, invalidApiaryMax;
	int validHiveMin, validHiveMid, validHiveMax, invalidHiveMin, invalidHiveMax;
	Player player;
	Field field;
	Garden garden;
	String validNameMin, validNameMid, validNameMax, invalidNameMin, invalidNameMax;

	@BeforeEach
	void setUp() throws Exception {
		field = new Field("Pollen Meadow");
		validNameMin = "a".repeat(3);
		validNameMid = "a".repeat(15);
		validNameMax = "a".repeat(35);
		invalidNameMin = "a".repeat(2);
		invalidNameMax = "a".repeat(36);
		validValueMin = 0;
		validValue = 20;
		invalidValue = -1;
		 
		validApiaryMax =1;
		invalidApiaryMin = -1;
		invalidApiaryMax = 2;
		
		validHiveMid = 2; 
		validHiveMax = 3; 
		invalidHiveMin = -1; 
		invalidHiveMax = 4;
		
		
		garden = new Garden(validNameMid, field, validValue, validValue);
	}

	//Test Garden constructor
	@Test
	void testGardenConstructor() {
		garden = new Garden(validNameMid, field, validValue, validValue);
		//Test the abstract parent constructor
		assertEquals(validNameMid, garden.getName());
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
			garden = new Garden(invalidNameMin, field, validValueMin, validValue);
		});assertEquals("Name must be 3-35 chars long", exp.getMessage());
		//Garden constructor tests
		exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(validNameMid, null, validValue, validValue);
		});assertEquals("Cannot be a null value", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(validNameMid, field, invalidValue, validValue);
		});assertEquals("Value cannot be less than 0", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, ()->{
			garden = new Garden(validNameMid, field, validValue, invalidValue);
		});assertEquals("Value cannot be less than 0", exp.getMessage());

	}
	
	
	@Test
	void testGetName() {
		garden.setName(validNameMin);
		assertEquals(validNameMin, garden.getName());
		garden.setName(validNameMid);
		assertEquals(validNameMid, garden.getName());
		garden.setName(validNameMax);
		assertEquals(validNameMax, garden.getName());
	}
	
	@Test
	void testSetNameInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, () -> {
			garden.setName(invalidNameMin);
		});assertEquals("Name must be 3-35 chars long", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, () -> {
			garden.setName(invalidNameMax);
		});assertEquals("Name must be 3-35 chars long", exp.getMessage());
	}

	@Test
	void testSetHive() {
		garden.setHives(validHiveMin);
		assertEquals(validHiveMin, garden.getHives());
		garden.setHives(validHiveMid);
		assertEquals(validHiveMid, garden.getHives());
		garden.setHives(validHiveMax);
		assertEquals(validHiveMax, garden.getHives());
		
		
	}
	@Test
	void testSetHiveInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, () -> {
			garden.setHives(invalidHiveMax);
		});
		assertEquals("The maximum Hives a garden can have is " + validHiveMax, exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, () -> {
			garden.setHives(invalidHiveMin);
		});
		assertEquals("Hives cannot be set to less than 0", exp.getMessage());
	}
	
	@Test
	void testSetApiary() {
		garden.setApiary(validApiaryMin);
		assertEquals(validApiaryMin, garden.getApiary());
		garden.setApiary(validApiaryMax);
		assertEquals(validApiaryMax, garden.getApiary());
	}
	@Test
	void testSetApiaryInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, () -> {
			garden.setApiary(invalidApiaryMin);
		});
		assertEquals("Apiaries canot be set to less than 0", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, () -> {
			garden.setApiary(invalidApiaryMax);
		});
		assertEquals("The maximum Apiaries a garden can have is " + validApiaryMax, exp.getMessage());
		
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

	@Test
	void testGetOwner() {
		player = new Player(validNameMid);
		garden.setOwner(player);
		assertEquals(player, garden.getOwner());
		
	}
	
	
	
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
	

}
