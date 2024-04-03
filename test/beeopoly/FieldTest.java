package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldTest {
	
	
	Field field;
	String validName;
	Garden garden;
	ArrayList<Garden> gardens;

	@BeforeEach
	void setUp() throws Exception {
		
		validName = "Blossom Estate";
		field = new Field(validName);
		garden = new Garden("Apple Blossom Grove", field, 400, 50, 10);
		gardens = new ArrayList<Garden>();
	}

	@Test
	void testFieldConstructor() {
		field = new Field(validName);
		assertEquals("Blossom Estate", field.getName());
	}
	@Test
	void testFieldConstructorInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, ()->{
			field = new Field(null);
		});assertEquals("Cannot be a null value", exp.getMessage());
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
		field.setName(validName);
		assertEquals(validName, field.getName());
	}
	
	@Test
	void testGetNameInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, ()->{
			field = new Field(null);
		});assertEquals("Cannot be a null value", exp.getMessage());
	}

}
