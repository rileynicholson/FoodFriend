# FoodFriend!

FoodFriend! is a Java program that helps a user come up with recipes to make with ingredients they have in their pantry. FoodFriend gives ideas ranging from something as small as a snack to as big as a main course meal. This project is currently in development, with more features and improvements planned for future updates.

# How to Run FoodFriend!
## Prerequisites
- **Java Development Kit:** I used Version 25 to make this project.
- **Spoonacular API Key:** I did not want people to misuse my personal API Key, so I stored it in a hidden file local to my computer. As a result, you need an API Key from Spoonacular. It is really easy to obtain one and you can obtain it from https://spoonacular.com/food-api for free.

## Instructions
### 1. Clone the repository onto your computer
```bash
git clone https://github.com/rileynicholson/FoodFriend.git
```

### Method 1: Using Eclipse IDE
2. Open Eclipse.
3. Navigate to File, then Import.
4. Select General, then Existing Projects into Workspace, and click Next.
5. Choose Select root directory, click Browse, and locate the clone location.
6. Click Finish.
7. Expand the project, navigate to `src`, then `Program`, then `Main.java`.
8. Run the file!

### Method 2: Using Command Prompt
2. Open Command Prompt and navigate to the project using 'cd'.
- If you did not change your directory before cloning, you should be able to use:
```bash
cd FoodFriend
```
3. Make sure you are in `FoodFriend\`. Compile the files into the bin directory using:
```bash
javac -d bin src\Program\*.java src\Tests\*.java
```
- If you run into problems with the command above, use:
```bash
javac -d bin src\Program\*.java
```
4. Run the application using:
```bash
java -cp bin Program.Main
```

## Before Running
- Make sure you add your API Key into the project. Navigate to `src/Program/Spoonacular.java` and follow the instructions in lines 18-20.

# Latest Updates to FoodFriend!
- Added a working menu.
- Implemented working inputs and error handling.
- Implemented the Spoonacular API.
- Created data storage of user's pantry.

# Upcoming Updates and Planned Features to FoodFriend!
- Cleaning up the classes.
- Cleaner design.
- More efficient programming solutions and structure.
