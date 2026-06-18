import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.IOException;

public class FoodFriend {
	static ArrayList<String> foodItems = new ArrayList<>();
	
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
					foodItems.add(temp[i]);
				}
			}
		} catch (IOException e) {
			System.out.println("Error: Save File exists, but is not found.");
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
					managePantry();
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
	}
	
	public static void managePantry() {
		// WIP
		// Work In Progress
	}
	
	public static void newPage() {
		for (int i = 0; i < 30; i++) {
			System.out.println();
		}
	}
}