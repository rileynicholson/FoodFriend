package Program;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class FoodFriend {
	
	static ArrayList<String> recipes = new ArrayList<>();
	static ArrayList<Ingredients> ingredients = new ArrayList<>();
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	/**
	 * Starts up the program.
	 */
	public void run() {
		File saveFile = new File("SaveFile.txt");
		
		if (saveFile.exists()) {
			readFile();
		}
		
		menu();
	}
	
	/**
	 * Reads the user's save file.
	 */
	public static void readFile() {	
		try (BufferedReader reader = new BufferedReader(new FileReader("SaveFile.txt"))) {
			String item;
			
			while ((item = reader.readLine()) != null) {
				String[] temp = item.split(",");
				Ingredients ingredient = new Ingredients(temp[0], null);
				
				if (!temp[1].equals("null")) {
					ingredient.setExpirationDate(LocalDate.parse(temp[1], formatter));
				}
				
				ingredients.add(ingredient);
			}
		} catch (IOException e) {
			System.out.println("ERROR: Save File exists, but is not found. No data has been loaded.\n");
		}
	}
	
	/**
	 * Saves the ingredients that the user has in a .txt file.
	 */
	public static void saveFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("SaveFile.txt", true))) {
			for (int i = 0; i < ingredients.size(); i++) {
				writer.write(ingredients.get(i).getName() + "," + ingredients.get(i).getExpirationDate() + "\n");
			}
		} catch (IOException e) {
			System.out.println("ERROR: Save file is not able to save. No data is saved.\n");
		}
	}
	
	/**
	 * Displays the main menu of the program.
	 */
	public static void menu() {
		Scanner scanner = new Scanner(System.in);
		int input = 0;
		
		while (true) {
			System.out.println("Food Friend\n-----------------");
			System.out.println("1. Generate Recipes\n"
					+ "2. Manage Pantry\n"
					+ "3. Exit\n"
					+ "\nEnter Option Here:");
			
			try {
				input = scanner.nextInt();
				scanner.nextLine();
				
				newPage();
				switch (input) {
					case 1: // Generate Recipes
						File saveFile = new File("SaveFile.txt");
					
						if (saveFile.exists() || !ingredients.isEmpty()) {
							generateRecipes();
						} else {
							System.out.println("Error: No ingredients found in the Pantry. Recipes cannot be generated.");
						}
						break;
				
					case 2: // Manage Pantry
						managePantry(scanner);
						break;
					
					case 3: // Exit
						System.out.println("Exiting the program, thank you for using!");
						scanner.close();
						System.exit(1);
						break;
					
					default: // Error
						System.out.println("Error: Input is not recognized. Please try again.");
						break;
				}
				
				System.out.println();
			} catch (Exception e) {
				newPage();
				System.out.println("Error: Input is not valid.\n");
			}
		}
	}
	
	/**
	 * Gathers recipes that the user can make from the API.
	 */
	public static void generateRecipes() {
		// Work In Progress (WIP)
		// Will call and interact with the API Spoonacular Class
	}
	
	/**
	 * Displays the menu for the 'Manage Pantry' option.
	 * 
	 * @param scanner for user input
	 */
	public static void managePantry(Scanner scanner) {
		newPage();
		File saveFile = new File("SaveFile.txt");
		String input;
		
		if (saveFile.exists()) {
			displayPantry();
			
			while (true) {
				boolean breakLoop = false;
				System.out.println("\nManage Pantry Options"
						+ "\n-----------------------------"
						+ "\n1. Modify Ingredient"
						+ "\n2. Remove Ingredient"
						+ "\n3. Exit\n"
						+ "\nEnter Option Here:");
				input = scanner.nextLine();
				
				switch (input) {
					case "1": // Modify Ingredient
						break;
					
					case "2": // Remove Ingredient
						break;
					
					case "3": // Exit
						newPage();
						breakLoop = true;
						break;
					
					default: // Input is not an option
						System.out.println("Error: Input is not recognized. Please try again.");
						break;
				}
				
				// Temporary solution for the exit option
				// Subject to change
				if (breakLoop) {
					break;
				}
				
				displayPantry();
			}
		} else {
			System.out.println("User pantry not found. Start inputing ingredients!"
					+ "\nYou will be prompted to enter the name of the ingredient, "
					+ "then the expiration date (if there is one)!\n");
			
			addIngredientsToPantry(scanner);
			saveFile();
		}
	}
	
	/**
	 * Adds ingredients to the user's pantry.
	 * 
	 * @param scanner for user input
	 */
	public static void addIngredientsToPantry(Scanner scanner) {
		String input;
		LocalDate date;
		
		while (true) {
			System.out.println("Enter the name of the ingredient "
					+ "(Say 'Stop' to stop inputting ingredients):");
			input = scanner.nextLine().toLowerCase();
			
			if (input.equals("stop")) {
				newPage();
				break;
			} else {
				Ingredients ingredient = new Ingredients(input, null);
				newPage();
				
				while (true) {
					System.out.println("Enter the expiration date of the ingredient in Year-Month-Day format "
							+ "(Say 'None' is there isn't one): ");
					input = scanner.nextLine();
					
					if (!input.equals("none")) {
						try {
							date = LocalDate.parse(input, formatter);
							
							if (date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now())) {
								System.out.println("\nError: The entered date is already expired, "
										+ "please try again.\n");
							} else {
								break;
							}
						} catch (DateTimeParseException e) {
							System.out.println("\nError: Date input is invalid. Please try again.\n");
						}
					} else {
						date = null;
						break;
					}
				}
				
				newPage();
				ingredient.setExpirationDate(date);
				
				while (true) {
					System.out.println("Is the given information entered correctly?\n"
							+ "\"" + ingredient.getName() + "\"" + ", " + "\"" + ingredient.getExpirationDate() + "\""
							+ "\n1. Yes"
							+ "\n2. No"
							+ "\nEnter option here: ");
					
					try {
						input = scanner.nextLine();
						
						switch (input) {
							case "1":
								ingredient.setName(capitalize(ingredient.getName()));
								ingredients.add(ingredient);
								newPage();
								System.out.println(ingredient.getName() + " added to pantry!\n");
								break;
							
							case "2":
								ingredient.setName(capitalize(ingredient.getName()));
								newPage();
								System.out.println(ingredient.getName() + " removed!\n");
								break;
							
							default:
								System.out.println("\nError: Input not recognized. Please try again.\n");
								break;
						}
					} catch (Exception e) {
						newPage();
						System.out.println("Error: Input is not valid. Please try again.\n");
					}
					
					// This is a error waiting to happen
					// Completely ends the loop even if the user entered something wrong
					// However, this is just a temporary placeholder to end this loop while I work on other features
					// This error will be fixed once I come up with a proper solution
					// WIP
					break;
				}
				
				// Originally wanted to put something here that would have the user redo their inputs
				// Just keeping this here as a reminder to myself just in case this current design doesn't work
			}
		}
	}
	
	/**
	 * Modifies an ingredient from the user's ingredient list.
	 * 
	 * @param scanner for gathering user input
	 */
	public static void modifyIngredient(Scanner scanner) {
		// Work In Progress (WIP)
	}
	
	/**
	 * Removes an ingredient from the user's ingredient list.
	 * 
	 * @param scanner for gathering user input
	 */
	public static void removeIngredient(Scanner scanner) {
		// Work In Progress (WIP)
	}
	
	/**
	 * Capitalizes a word if it is considered lower case for formatting.
	 * 
	 * @param name the word 
	 * @return the now capitalized word
	 */
	public static String capitalize(String name) {
		String firstLetterReplace = "", firstLetterSearch = "";
		
		firstLetterReplace += name.charAt(0);
		firstLetterReplace = firstLetterReplace.toUpperCase();
		
		firstLetterSearch += name.charAt(0);
		
		name = name.replaceFirst(firstLetterSearch, firstLetterReplace);
		
		return name;
	}
	
	/**
	 * Displays the user's pantry to the user.
	 */
	public static void displayPantry() {
		try (BufferedReader reader = new BufferedReader(new FileReader("SaveFile.txt"))) {
			String line;
			ingredients.clear();
			
			while ((line = reader.readLine()) != null) {
				Ingredients ingredient = new Ingredients(null, null);
				String[] temp = line.split(",");
				
				ingredient.setName(temp[0]);
				
				if (!temp[1].equals("null")) {
					ingredient.setExpirationDate(LocalDate.parse(temp[1], formatter));
				}
				
				ingredients.add(ingredient);
			}
		} catch (IOException e) {
			System.out.println("Error: Save file exists, but cannot be found.\n");
		}
		
		System.out.println("Pantry:\n\n");
		for (int i = 0; i < ingredients.size(); i++) {
			int ingredientsOnRow = 0;
			//LocalDate[] dates = new LocalDate[3];
			LocalDate[] dates = {null, null, null};
			
			while (ingredientsOnRow < 3 && i < ingredients.size()) {
				System.out.printf("%-24s", ingredients.get(i).getName());
				dates[ingredientsOnRow] = ingredients.get(i).getExpirationDate();
				ingredientsOnRow++;
				i++;
			}
			
			System.out.println("\n");
			for (int j = 0; j < ingredientsOnRow; j++) {
				System.out.printf("%-24s", dates[j]);
			}
			
			System.out.println("\n----------------------------------------------------------------\n");
			i--;
		}
	}
	
	/**
	 * Spreads the program's outputs out using print statements to signify a new page.
	 */
	public static void newPage() {
		for (int i = 0; i < 30; i++) {
			System.out.println();
		}
	}
}