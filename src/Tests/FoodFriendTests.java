package Tests;

import Program.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests for the FoodFriend class.
 */
class FoodFriendTests {
	
	@Test
	void testCapitalize_RegularWord() {
		String testWord = "lowercase";
		String newTestWord = FoodFriend.capitalize(testWord);
		
		assertNotEquals(testWord, newTestWord);
		assertEquals(newTestWord, "Lowercase");
	}
	
	@Test
	void testCapitalize_WordWithMultipleOfSameFirstLetter() {
		String testWord = "tomato";
		String newTestWord = FoodFriend.capitalize(testWord);
		
		assertNotEquals(testWord, newTestWord);
		assertNotEquals(newTestWord, "TomaTo");
		assertEquals(newTestWord, "Tomato");
	}
}