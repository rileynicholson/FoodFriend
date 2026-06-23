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
					
					if (saveFile.exists()) {
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
		// WIP
		// Work In Progress
		
		newPage();
		File saveFile = new File("SaveFile.txt");
		String input;
		LocalDate date;
		
		if (saveFile.exists()) {
			displayPantry();
		} else {
			System.out.println("User pantry not found. Start inputing ingredients!"
					+ "You will be prompted to enter the name of the ingredient, then the expiration date (if there is one)!\n");
			
			while (true) {
				System.out.println("Enter the name of the ingredient (Say 'Stop' to stop inputting ingredients):");
				input = scanner.nextLine().toLowerCase();
				
				if (input.equals("stop")) {
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
								// Something that will move the user to the inputs of the next ingredient
								// WIP
								break;
								
							case "2":
								// Something that will make the user redo the inputs of the current ingredient
								// WIP
								break;
								
							default:
								System.out.println("\nError: Input not recognized.");
								break;
							}
						} catch (Exception e) {
							
						}
					}
					
					// Something that will make the user redo the inputs of the ingredient and the expiration date
					// WIP
				}
			}
		}
	}
	
	public static void displayPantry() {
		try (BufferedReader reader = new BufferedReader(new FileReader("SaveFile.txt"))) {
			
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