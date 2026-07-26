package Program;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;

public class Spoonacular {
	
	// This is for those who want to run the program and have their own API key.
	// Add your own key in the quotation marks in the commented line of code below.
	// Then comment out the original line of code that sets the key value with the "gatherAPIKey()" method.
	//
	// private static final String key = "";
	
	private static final String key = gatherAPIKey();
	private static final String baseURL = "https://api.spoonacular.com/recipes/complexSearch";
	
	/**
	 * Gathers recipes that the user can make based on their ingredients.
	 * 
	 * @param ingredients the users have in their pantry
	 * @param dish what type of food they want to make (main course, snack, etc)
	 */
	public static void getRecipes(ArrayList<Ingredient> ingredients, String dish) {
		String urlString, query = "";
		HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
		
		switch (dish) {
		case "Breakfast":
			dish = "breakfast";
			break;
			
		case "Lunch":
			dish = "main%20course";
			break;
			
		case "Dinner":
			dish = "main%20course";
			break;
			
		case "Dessert":
			dish = "dessert";
			break;
			
		case "Snack":
			dish = "snack";
			break;
		}
		
		for (int i = 0; i < ingredients.size(); i++) {
			query += ingredients.get(i).getName();
			
			if (i < ingredients.size() - 1) {
				query += ",";
			}
		}
		
		urlString = String.format("%s?includeIngredients=%s&number=50&ranking=1&ignorePantry=true&type=%s&apiKey=%s", baseURL, query, dish, key);
		
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlString)).GET().build();
		
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			if (response.statusCode() == 200) { // 200 is HTTP code for successful response
				String formatted = response.body().replace("{", "\n")
						.replace("}", "\n")
						.replace(",", "\n");
				
				try (BufferedWriter writer = new BufferedWriter(new FileWriter("recipes.json", false))) {
					writer.write(formatted);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				storeRecipes();
			} else {
				System.out.println("Request failed. Status code: " 
						+ response.statusCode() + " " + response.body());
			}
		} catch (Exception e) {
			FoodFriend.newPage();
			e.printStackTrace();
			System.out.println("Error: Recipes could not be gathered from API, "
					+ "program ending..");
			
			System.exit(-1);
		}
	}
	
	/**
	 * Stores the recipes generated in ArrayList.
	 */
	public static void storeRecipes() {
		try (BufferedReader reader = new BufferedReader(new FileReader("recipes.json"))) {
			String line, temp;
			
			while ((line = reader.readLine()) != null) {
				if (line.contains("title")) {
					temp = line.replace("\"title\":", "")
							.replace("\"", "");
					
					FoodFriend.capitalize(temp);
					FoodFriend.recipes.add(temp);
				}
			}
		} catch (IOException e) {
			// Temporary Error condition
			// Subject to change
			// Work in progress (WIP)
			FoodFriend.newPage();
			e.printStackTrace();
			System.out.println("Error: Recipes could not be gathered from recipes file, "
					+ "program ending..");
			
			System.exit(-1);
		}
	}
	
	/**
	 * Gathers the API Key from hidden .env local to my PC. Read the README for more information.
	 * 
	 * @return the API key
	 */
	public static String gatherAPIKey() {
		String API = "";
		
		try (BufferedReader reader = new BufferedReader(new FileReader("." + File.separator + ".env"))) {
			String[] temp = reader.readLine().split("=");
			API = temp[1];
		} catch (IOException e) {
			// Temporary Error condition
			// Subject to change
			// Work in progress (WIP)
			FoodFriend.newPage();
			e.printStackTrace();
			System.out.println("Error: API Key could not be gathered, "
					+ "please go into the Spoonacular class and add your own Spoonacular API Key or please try again "
					+ "program ending..");
			
			System.exit(-1);
		}
		
		return API;
	}
}