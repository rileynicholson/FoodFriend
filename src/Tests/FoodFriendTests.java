package Tests;

import Program.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test cases for the FoodFriend class.
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
	void testCapitalize_NotRegularWord() {
		String testWord = "ncrwlq";
		String newTestWord = FoodFriend.capitalize(testWord);
		
		assertNotEquals(testWord, newTestWord);
		assertEquals(newTestWord, "Ncrwlq");
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
	void testCapitalize_WordAlreadyCapitalized() {
		String testWord = "Tomato";
		String newTestWord = FoodFriend.capitalize(testWord);
		
		assertEquals(testWord, newTestWord);
		assertEquals(newTestWord, "Tomato");
	}
	
	@Test
	void testLinearSearch_WithNormalTerm() {
		Ingredient ingredient1 = new Ingredient("Tomato", null);
		
		FoodFriend.ingredients.add(ingredient1);
		int index = FoodFriend.linearSearch("Tomato");
		
		assertNotEquals(index, -1);
		assertEquals(index, 0);
	}
	
	@Test
	void testLinearSearch_WithLowercaseTerm() {
		Ingredient ingredient1 = new Ingredient("Tomato", null);
		
		FoodFriend.ingredients.add(ingredient1);
		int index = FoodFriend.linearSearch("tomato");
		
		assertNotEquals(index, -1);
		assertEquals(index, 0);
	}
	
	@Test
	void testLinearSearch_WithTermThatIsNotInsideIngredients() {
		Ingredient ingredient1 = new Ingredient("Tomato", null);
		
		FoodFriend.ingredients.add(ingredient1);
		int index = FoodFriend.linearSearch("orange");
		
		assertNotEquals(index, 0);
		assertEquals(index, -1);
	}
}