package Tests;
import Program.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FoodFriendTests {
	// For testing methods within FoodFriend.java and their expected outputs
	
	@Test
	void testCapitalize() {
		String testWord = "lowercase";
		String newTestWord = FoodFriend.capitalize(testWord);
		
		assertNotEquals(testWord, newTestWord);
		assertEquals(newTestWord, "Lowercase");
	}

}
