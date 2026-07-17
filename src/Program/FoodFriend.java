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
	
	public static ArrayList<String> recipes = new ArrayList<>();
	public static ArrayList<Ingredient> ingredients = new ArrayList<>();
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
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
				Ingredient ingredient = new Ingredient(temp[0], null);
				
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
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("SaveFile.txt", false))) {
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
							generateRecipes(scanner);
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
					
					default: // Unrecognized input
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
	 * 
	 * @param scanner for user input
	 */
	public static void generateRecipes(Scanner scanner) {
		newPage();
		File saveFile = new File("SaveFile.txt");
		String input;
		
		if (saveFile.exists() && !ingredients.isEmpty()) {
			while (true) {
				boolean breakLoop = false;
				System.out.println("What kind of recipe do you want?"
						+ "\n1. Main Course"
						+ "\n2. Dessert"
						+ "\n3. Snack"
						+ "\n4. Exit\n"
						+ "\nEnter option here: ");
				input = scanner.nextLine();
				
				newPage();
				switch (input) {
				case "1": // Main Course
					break;
					
				case "2": // Dessert
					break;
					
				case "3": // Snack
					break;
					
				case "4": // Exit
					breakLoop = true;
					break;
					
				default: // Unrecognized input
					System.out.println("Error: Input is not recognized. Please try again.\n");
					break;
				}
				
				// Temporary solution for the exit option
				// Subject to change
				if (breakLoop) {
					break;
				}
			}
		} else {
			System.out.println("Error: No ingredients found. "
					+ "Recipes cannot be generated.");
		}
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
		
		if (saveFile.exists() && !ingredients.isEmpty()) {
			displayPantry();
			
			while (true) {
				boolean breakLoop = false;
				System.out.println("\nManage Pantry Options"
						+ "\n-----------------------------"
						+ "\n1. Add Ingredient"
						+ "\n2. Modify Ingredient"
						+ "\n3. Remove Ingredient"
						+ "\n4. Exit\n"
						+ "\nEnter Option Here:");
				input = scanner.nextLine();
				
				newPage();
				switch (input) {
					case "1": // Add Ingredient
						addIngredientsToPantry(scanner);
						break;
				
					case "2": // Modify Ingredient
						modifyIngredient(scanner);
						break;
					
					case "3": // Remove Ingredient
						removeIngredient(scanner);
						break;
					
					case "4": // Exit
						breakLoop = true;
						break;
					
					default: // Unrecognized input
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
				Ingredient ingredient = new Ingredient(input, null);
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
					
					input = scanner.nextLine();
					
					if (input.equals("1")) {
						ingredient.setName(capitalize(ingredient.getName()));
						ingredients.add(ingredient);
						
						saveFile();
						newPage();
						
						System.out.println(ingredient.getName() + " added to pantry!\n");
						break;
					} else if (input.equals("2")) {
						ingredient.setName(capitalize(ingredient.getName()));
						
						newPage();
						
						System.out.println(ingredient.getName() + " removed!\n");
						break;
					} else {
						newPage();
						System.out.println("\nError: Input not recognized. Please try again.\n");
					}
				}
			}
		}
	}
	
	/**
	 * Modifies an ingredient from the user's ingredient list.
	 * 
	 * @param scanner for gathering user input
	 */
	public static void modifyIngredient(Scanner scanner) {
		String input, temp;
		boolean dateWorks = true;
		int index = 0;
		displayIngredients();
		
		while (true) {
			System.out.println("\nEnter the ingredient's name you want to modify "
					+ "(Enter 'exit' to go back to the menu): ");
			input = scanner.nextLine();
			
			if (input.toLowerCase().equals("exit")) {
				break;
			}
			
			newPage();
			
			index = linearSearch(input);
			
			if (index != -1) {
				break;
			} else {
				displayIngredients();
				System.out.println("\nError: Ingredient not found "
						+ "in the user's list of ingredients. "
						+ "Please try again.");
			}
		}
		
		if (!input.toLowerCase().equals("exit")) {
			while (true) {
				System.out.println("How would you like to modify this ingredient? "
						+ "\n\"" + ingredients.get(index).getName() + "\"" + ", " + "\"" + ingredients.get(index).getExpirationDate() + "\""
						+ "\n1. Name"
						+ "\n2. Expiration Date"
						+ "\n3. Cancel"
						+ "\nEnter option here: ");
				input = scanner.nextLine();
				
				newPage();
				if (input.equals("1")) {
					while (true) {
						System.out.println("Replacing \"" + ingredients.get(index).getName() + "\".."
								+ "\nEnter the new name here: ");
						temp = scanner.nextLine();
						
						newPage();
						
						System.out.println("New name: "
								+ "\"" + temp + "\""
								+ "\nIs this the correct new name of the ingredient?"
								+ "\n1. Yes"
								+ "\n2. No"
								+ "\nEnter option: ");
						input = scanner.nextLine();
						
						newPage();
						if (input.equals("1")) {
							ingredients.get(index).setName(temp);
							saveFile();
							break;
						} else if (input.equals("2")) {
							continue;
						} else {
							System.out.println("Error: Input is not recognized, "
									+ "please try again.");
						}
					}
				} else if (input.equals("2")) {
					while (true) {
						System.out.println("Replacing \"" + ingredients.get(index).getExpirationDate() + "\".."
								+ "\nEnter the new expiration date here (Year-Month-Day format): ");
						temp = scanner.nextLine();
						
						try {
							if (LocalDate.parse(temp, formatter).isAfter(LocalDate.now())) {
								dateWorks = true;
							} else {
								newPage();
								
								System.out.println("Error: Input is expired, "
										+ "please try again.\n");
								dateWorks = false;
							}
						} catch (Exception e) {
							newPage();
							
							System.out.println("Error: Input is not an actual date, "
									+ "please try again.\n");
							dateWorks = false;
						}
						
						if (dateWorks) {
							newPage();
							
							System.out.println("New expiration date: "
									+ "\"" + temp + "\""
									+ "\nIs this the correct new expiration date of the ingredient?"
									+ "\n1. Yes"
									+ "\n2. No"
									+ "\nEnter option: ");
							input = scanner.nextLine();
							
							newPage();
							if (input.equals("1")) {
								ingredients.get(index).setExpirationDate(LocalDate.parse(temp, formatter));
								saveFile();
								break;
							} else if (input.equals("2")) {
								continue;
							} else {
								System.out.println("Error: Input is not recognized, "
										+ "please try again.\n");
							}
						}
					}
				} else if (input.equals("3")) {
					break;
				} else {
					newPage();
					System.out.println("Error: Input not recognized, "
							+ "please try again.\n");
				}
			}
		}
	}
	
	/**
	 * Removes an ingredient from the user's ingredient list.
	 * 
	 * @param scanner for gathering user input
	 */
	public static void removeIngredient(Scanner scanner) {
		String input;
		int index = 0;
		displayIngredients();
		
		while (true) {
			System.out.println("\nEnter the ingredient you want to remove "
					+ "(Enter 'exit' to go back to menu): ");
			input = scanner.nextLine();
			
			if (input.toLowerCase().equals("exit")) {
				break;
			}
			
			newPage();
			
			index = linearSearch(input);
			
			if (index != -1) {
				break;
			} else {
				displayIngredients();
				System.out.println("\nError: Ingredient not found "
						+ "in the user's list of ingredients. "
						+ "Please try again.");
			}
		}
		
		if (!input.toLowerCase().equals("exit")) {
			while (true) {
				System.out.println("Are you sure you want to remove this ingredient? "
						+ "\n\"" + ingredients.get(index).getName() + "\"" + ", " + "\"" + ingredients.get(index).getExpirationDate() + "\""
						+ "\n1. Yes"
						+ "\n2. No"
						+ "\nEnter option here: ");
				input = scanner.nextLine();
				
				if (input.equals("1")) {
					ingredients.remove(index);
					
					saveFile();
					
					newPage();
					break;
				} else if (input.equals("2")) {
					newPage();
					break;
				} else {
					newPage();
					System.out.println("Error: Input not recognized, "
							+ "please try again.");
				}
			}
		}
	}
	
	/**
	 * Searches for an ingredient in the ArrayList of user ingredients using a Linear Search Algorithm.
	 * 
	 * @param target the search term
	 * @return the index value of the ingredient if it exists
	 */
	public static int linearSearch(String target) {
		target = capitalize(target);
		
		for (int i = 0; i < ingredients.size(); i++) {
			if (target.equals(ingredients.get(i).getName())) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Capitalizes a word if considered lower case for formatting.
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
	 * Displays the user's pantry to the user with formatting.
	 */
	public static void displayPantry() {
		try (BufferedReader reader = new BufferedReader(new FileReader("SaveFile.txt"))) {
			String line;
			ingredients.clear();
			
			while ((line = reader.readLine()) != null) {
				Ingredient ingredient = new Ingredient(null, null);
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
		
		System.out.println("Pantry:"
				+ "\n------------\n");
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
			
			System.out.println("\n-------------------------------"
					+ "---------------------------------\n");
			i--;
		}
	}
	
	/**
	 * Displays the user's ingredients without any formatting.
	 */
	public static void displayIngredients() {
		try (BufferedReader reader = new BufferedReader(new FileReader("SaveFile.txt"))) {
			String line;
			ingredients.clear();
			
			while ((line = reader.readLine()) != null) {
				Ingredient ingredient = new Ingredient(null, null);
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
		
		System.out.println("Ingredients:");
		
		for (int i = 0; i< ingredients.size(); i++) {
			System.out.println(ingredients.get(i).getName() + 
					", " + ingredients.get(i).getExpirationDate());
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