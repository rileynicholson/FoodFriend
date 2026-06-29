package Tests;
import Program.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FoodFriendTests {
	
	@Test
	void testCapitalize() {
		String testWord = "lowercase";
		String newTestWord = FoodFriend.capitalize(testWord);
		
		assertNotEquals(testWord, newTestWord);
		assertEquals(newTestWord, "Lowercase");
	}
	
	@Test
	void testCapitalize2() {
		String testWord = "tomato"; // A word that contains multiple of the same first letter
		String newTestWord = FoodFriend.capitalize(testWord);
		
		assertNotEquals(testWord, newTestWord);
		assertNotEquals(newTestWord, "TomaTo"); // Incorrect output the method used to give
		assertEquals(newTestWord, "Tomato");
	}
	
}
