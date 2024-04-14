package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldTest {
	
	
	Field field;
	String validNameMin, validNameMid, validNameMax, invalidNameMin, invalidNameMax;
	Garden garden;
	ArrayList<Garden> gardens;

	@BeforeEach
	void setUp() throws Exception {
		
		validNameMin = "a".repeat(3);
		validNameMid = "a".repeat(12);
		validNameMax = "a".repeat(25);
		invalidNameMin = "a".repeat(2);
		invalidNameMax = "a".repeat(26);
		field = new Field(validNameMid);
		garden = new Garden("Apple Blossom Grove", field, 400, 50);
		gardens = new ArrayList<Garden>();
	}

	@Test
	void testFieldConstructor() {
		field = new Field(validNameMid);
		assertEquals(validNameMid, field.getName());
	}
	@Test
	void testFieldConstructorInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, ()->{
			field = new Field(invalidNameMin);
		});assertEquals("Buzz off! Field must be 3-25 chars long", exp.getMessage());
	}

	
	@Test
	void testAddGarden() {
		//Test that the ArrayList is empty
		assertEquals(0, field.getGardens().size());
		//Add garden and check size
		field.addGarden(garden);
		assertEquals(1, field.getGardens().size());

	}

	@Test
	void testGetName() {
		field.setName(validNameMin);
		assertEquals(validNameMin, field.getName());
		field.setName(validNameMid);
		assertEquals(validNameMid, field.getName());
		field.setName(validNameMax);
		assertEquals(validNameMax, field.getName());
	}
	
	@Test
	void testGetNameInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, ()->{
			field.setName(invalidNameMin);
		});assertEquals("Buzz off! Field must be 3-25 chars long", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, ()->{
			field.setName(invalidNameMax);
		});assertEquals("Buzz off! Field must be 3-25 chars long", exp.getMessage());
		exp = assertThrows(IllegalArgumentException.class, ()->{
			field.setName(null);
		});assertEquals("Buzz off! Field cannot be null", exp.getMessage());
	}

}
