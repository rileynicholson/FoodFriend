package Program;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FoodFriend {
	static ArrayList<String> recipes = new ArrayList<>();
	static ArrayList<Ingredients> ingredients = new ArrayList<>();
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public void run() {
		File saveFile = new File("SaveFile.txt");
		
		if (saveFile.exists()) {
			readFile();
		}
		
		menu();
	}
	
	public static void readFile() {	
		try (BufferedReader reader = new BufferedReader(new FileReader("SaveFile.txt"))) {
			String item;
			
			while ((item = reader.readLine()) != null) {
				String[] temp = item.split(",");
				
				for (int i = 0; i < temp.length; i++) {
					Ingredients ingredient = new Ingredients(temp[i], LocalDate.parse(temp[i+1], formatter));
					ingredients.add(ingredient);
				}
			}
		} catch (IOException e) {
			// Temporary procedure for error
			// Placeholder sequence for now
			System.out.println("Error: Save File exists, but is not found.");
			System.exit(-1);
		}
	}
	
	public static void writeFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("SaveFile.txt", true))) {
			for (int i = 0; i < ingredients.size(); i++) {
				writer.write(ingredients.get(i).getName() + "," + ingredients.get(i).getExpirationDate());
			}
		} catch (IOException e) {
			// Temporary procedure for error
			// Placeholder sequence for now
			System.out.println("Error: Not able to save.");
			System.exit(-1);
		}
	}
	
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
					
					if (saveFile.exists()||!ingredients.isEmpty()) {
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
				scanner.nextLine();
			}
		}
	}
	
	public static void generateRecipes() {
		// WIP
		// Work In Progress
		// Will call and interact with the API Class
	}
	
	public static void managePantry(Scanner scanner) {
		newPage();
		File saveFile = new File("SaveFile.txt");
		String input;
		LocalDate date; // Variable might be removed down the line as this method gets designed
		
		if (saveFile.exists()) {
			displayPantry();
		} else {
			System.out.println("User pantry not found. Start inputing ingredients!"
					+ "\nYou will be prompted to enter the name of the ingredient, then the expiration date (if there is one)!\n");
			
			addIngredientsToPantry(scanner);
		}
		
		// Prompts the user options of what to do after their pantry is displayed
		// I will complete the 'displayPantry()' method then work on the rest of this method
		// WIP
	}
	
	public static void addIngredientsToPantry(Scanner scanner) {
		String input;
		LocalDate date;
		
		while (true) {
			System.out.println("Enter the name of the ingredient (Say 'Stop' to stop inputting ingredients):");
			input = scanner.nextLine().toLowerCase();
			
			if (input.equals("stop")) {
				newPage();
				break;
			} else {
				Ingredients ingredient = new Ingredients(input, null);
				newPage();
				
				while (true) {
					System.out.println("Enter the expiration date of the ingredient in Year-Month-Day format (Say 'None' is there isn't one): ");
					input = scanner.nextLine();
					
					try {
						date = LocalDate.parse(input, formatter);
						
						if (date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now())) {
							System.out.println("\nError: The entered date is already expired, please try again.\n");
						} else {
							break;
						}
					} catch (DateTimeParseException e) {
						System.out.println("\nError: Date input is invalid. Please try again.\n");
					}
				}
				
				newPage();
				ingredient.setExpirationDate(date);
				
				while (true) {
					System.out.println("Is the given information entered correctly?\n"
							+ "\"" + ingredient.getName() + "\"" + ", " + "\"" + ingredient.getExpirationDate()
							+ "\n1. Yes"
							+ "\n2. No"
							+ "\nEnter option here: ");
					
					try {
						input = scanner.nextLine();
						
						switch(input) {
						case "1":
							ingredient.setName(capitalize(ingredient.getName()));
							ingredients.add(ingredient);
							newPage();
							System.out.println(ingredient.getName() + " added to pantry!\n");
							break;
							
						case "2":
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
						scanner.nextLine();
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
	
	// For formatting
	// Just in case the user enters a lower case ingredient
	public static String capitalize(String name) {
		String firstLetter = "";
		
		firstLetter += name.charAt(0);
		firstLetter = firstLetter.toUpperCase();
		
		name = name.replace(name.charAt(0), firstLetter.charAt(0));
		
		return name;
	}
	
	public static void displayPantry() {
		try (BufferedReader reader = new BufferedReader(new FileReader("SaveFile.txt"))) {
			// Displays the user's pantry
			// WIP
		} catch (IOException e) {
			System.out.println("Error: Save file exists, but cannot be found.\n");
		}
	}
	
	public static void newPage() {
		for (int i = 0; i < 30; i++) {
			System.out.println();
		}
	}
}