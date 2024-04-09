package beeopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NectarOasisTest {

	NectarOasis necOasis;
	String validNameMid,  invalidNameMin;
	
	@BeforeEach
	void setUp() throws Exception {
		
		validNameMid = "a".repeat(15);
		invalidNameMin = "a".repeat(2);
		
		necOasis = new NectarOasis(validNameMid);
	}

	@Test
	void testNectarOasisConstructor() {
		necOasis = new NectarOasis(validNameMid);
		assertEquals(validNameMid, necOasis.getName());
	}
	@Test
	void testNectarOasisConstructorInvalid() {
		Exception exp = assertThrows(IllegalArgumentException.class, ()->{
			necOasis = new NectarOasis(invalidNameMin);
		});assertEquals("Name must be 3-35 chars long", exp.getMessage());
	}

}
