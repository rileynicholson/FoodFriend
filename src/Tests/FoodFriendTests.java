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
	
	@Test
	void testLinearSearch_WithNormalTerm() {
		Ingredients ingredient1 = new Ingredients("Tomato", null);
		
		FoodFriend.ingredients.add(ingredient1);
		int index = FoodFriend.linearSearch("Tomato");
		
		assertNotEquals(index, -1);
		assertEquals(index, 0);
	}
	
	@Test
	void testLinearSearch_WithLowercaseTerm() {
		Ingredients ingredient1 = new Ingredients("Tomato", null);
		
		FoodFriend.ingredients.add(ingredient1);
		int index = FoodFriend.linearSearch("tomato");
		
		assertNotEquals(index, -1);
		assertEquals(index, 0);
	}
}