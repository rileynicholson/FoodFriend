package Tests;

import Program.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * Tests for the Ingredient class.
 */
class IngredientTests {
	
	private Ingredient userIngredient;
	
	@BeforeEach
	void setUp() {
		userIngredient = new Ingredient("Orange", LocalDate.of(2026, 12, 12));
	}
	
	@Test
	void testGetName() {
		assertEquals(userIngredient.getName(), "Orange");
	}
	
	@Test
	void testGetExpirationDate() {
		assertEquals(userIngredient.getExpirationDate(), LocalDate.of(2026, 12, 12));
	}
	
	@Test
	void testSetName() {
		assertEquals(userIngredient.getName(), "Orange");
		assertNotEquals(userIngredient.getName(), "Apple");
		
		userIngredient.setName("Apple");
		
		assertEquals(userIngredient.getName(), "Apple");
		assertNotEquals(userIngredient.getName(), "Orange");
	}
	
	@Test
	void testSetExpirationDate() {
		assertEquals(userIngredient.getExpirationDate(), LocalDate.of(2026, 12, 12));
		assertNotEquals(userIngredient.getExpirationDate(), LocalDate.of(2026, 12, 04));
		
		userIngredient.setExpirationDate(LocalDate.of(2026, 12, 04));
		
		assertEquals(userIngredient.getExpirationDate(), LocalDate.of(2026, 12, 04));
		assertNotEquals(userIngredient.getExpirationDate(), LocalDate.of(2026, 12, 12));
	}
}