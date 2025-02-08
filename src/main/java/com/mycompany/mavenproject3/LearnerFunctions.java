package com.mycompany.mavenproject3;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LearnerFunctions {
    private final Library library;
    private final Scanner scanner;
    private Learner LoggedInLearner;

    public LearnerFunctions(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

    public void setLoggedInLearner(Learner learner) {
        this.LoggedInLearner = learner;
    }

    // Methods for viewing books and learner information
    public void ViewAvailableBooks() {
        ClearScreen();
        System.out.println("\nAvailable Books");
        List<Material> materials = library.GetMaterials();
        if (materials.isEmpty()) {
            System.out.println("No books in the library yet.");
            return;
        }
        for (Material material : library.GetMaterials()) {
            if (material.GetCopies() > 0) {
                System.out.println("Material ID: " + material.GetMaterialID());
                System.out.println("Title: " + material.GetTitle());
                System.out.println("Author: " + material.GetAuthor());
                System.out.println("Publisher: " + material.GetPublisher());
                System.out.println("Year Published: " + material.GetYearPublished());
                System.out.println("Copies: " + material.GetCopies());
                System.out.println("Type: " + material.GetType());
                System.out.println();
            }
        }
    }

    public void ViewBorrowedBooks() {
        ClearScreen();
        if (LoggedInLearner == null) {
            System.out.println("You must be logged in to view your borrowed books.");
            return;
        }
        System.out.println("\nBorrowed Books");
        List<String> borrowedMaterials = LoggedInLearner.GetBorrowedMaterials();
        if (borrowedMaterials.isEmpty()) {
            System.out.println("You have not borrowed any books.");
            return;
        }
        for (String materialID : borrowedMaterials) {
            Material material = library.FindMaterial(Integer.parseInt(materialID));
            if (material != null) {
                System.out.println("Material ID: " + material.GetMaterialID());
                System.out.println("Title: " + material.GetTitle());
                System.out.println("Author: " + material.GetAuthor());
                System.out.println("Publisher: " + material.GetPublisher());
                System.out.println("Year Published: " + material.GetYearPublished());
                System.out.println("Type: " + material.GetType());
                System.out.println();
            }
        }
    }

    public void ViewViolations() {
        ClearScreen();
        if (LoggedInLearner == null) {
            System.out.println("You must be logged in to view your violations.");
            return;
        }
        System.out.println("\nViolations");
        System.out.println("Number of Violations: " + LoggedInLearner.GetNumberOfViolations());
    }

    public void ViewLearnerInformation() {
        ClearScreen();
        if (LoggedInLearner == null) {
            System.out.println("You must be logged in to view your information.");
            return;
        }
        System.out.println("\nMy Information");
        System.out.println("Student ID: " + LoggedInLearner.GetStudentID());
        System.out.println("Name: " + LoggedInLearner.GetFirstName() + " " + LoggedInLearner.GetLastName());
        System.out.println("Gender: " + LoggedInLearner.GetGender());
        System.out.println("Birthday: " + LoggedInLearner.GetBirthday());
        System.out.println("Contact Number: " + LoggedInLearner.GetContactNum());
        System.out.println("Email: " + LoggedInLearner.GetEmail());
        System.out.println("Address: " + LoggedInLearner.GetAddress());
        System.out.println("Number of Violations: " + LoggedInLearner.GetNumberOfViolations());
    }

    // Methods for borrowing and returning books
    public void BorrowBook() {
        ClearScreen();
        ViewAvailableBooks();
        try {
            List<Material> materials = library.GetMaterials();
            if (materials.isEmpty()) {
                System.out.println("No books available in the system yet.");
                System.out.println("Press enter to continue...");
                return;
            }
            ViewAvailableBooks();
            System.out.println("\nBorrow Book");
            int materialID = getIntInput("Enter Material ID: ");
            Material material = library.FindMaterial(materialID);
            if (material == null) {
                System.out.println("Material not found.");
                System.out.println("Press enter to continue...");
                scanner.nextLine(); // Wait for the user to press Enter 
                return;
            }
            if (material.GetCopies() <= 0) {
                System.out.println("No copies available.");
                System.out.println("Press enter to continue...");
                scanner.nextLine(); // Wait for the user to press Enter
                return;
            }
            material.SetCopies(material.GetCopies() - 1);
            System.out.println("Book borrowed successfully.");
            System.out.println("Press enter to continue...");
            scanner.nextLine(); // Wait for the user to press Enter
        } catch (Exception e) {
            ClearScreenY();
            System.out.println("Error borrowing book: " + e.getMessage());
        }
    }

    public void ReturnBook() {
        ClearScreen();
        if (LoggedInLearner == null) {
            System.out.println("You must be logged in to return a book.");
            return;
        }
        ViewBorrowedBooks();
        System.out.println("\nReturn Book");
        int materialID = getIntInput("Enter Material ID: ");
        Material material = library.FindMaterial(materialID);
        if (material == null) {
            System.out.println("Material not found.");
            return;
        }
        LoggedInLearner.ReturnMaterial(materialID);
        material.SetCopies(material.GetCopies() + 1);
        System.out.println("Book returned successfully.");
        System.err.println("Press enter to continue...");
        scanner.nextLine();
    }

    // Methods for managing learners
    public void LearnerActionsMenu() {
        ClearScreen();
        while (true) {
            System.out.println("\nWelcome " + LoggedInLearner.GetFirstName() + " " + LoggedInLearner.GetLastName());
            System.out.println("1. View available books.");
            System.out.println("2. Borrow a book.");
            System.out.println("3. Return a book.");
            System.out.println("4. View borrowed books.");
            System.out.println("5. View violations.");
            System.out.println("6. View your information.");
            System.out.println("7. Logout.");
            int choice = getIntInput(":: ");
            switch (choice) {
                case 1 -> ViewAvailableBooks();
                case 2 -> BorrowBook();
                case 3 -> ReturnBook();
                case 4 -> ViewBorrowedBooks();
                case 5 -> ViewViolations();
                case 6 -> ViewLearnerInformation();
                case 7 -> {
                    LoggedInLearner = null;
                    return; // Return to the main menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void RegisterLearner() {
        ClearScreen();
        System.out.println("\nRegister Learner");
        
        // Get available block first
        Block availableBlock = library.GetAvailableBlock();
        if (availableBlock == null) {
            System.out.println("Sorry, all blocks are currently full. Registration is not possible at this time.");
            System.out.println("Press enter to continue...");
            scanner.nextLine();
            return;
        }

        // Continue with normal registration
        String firstName = getStringInput("Enter first name: ");
        String lastName = getStringInput("Enter last name: ");
        String middleName = getStringInput("Enter middle name: ");
        String gender = getStringInput("Enter gender: ");
        String birthday = getBirthdayInput("Enter birthday (YYYY-MM-DD or YYYY/MM/DD): ");
        int contactNum = getIntInput("Enter contact number: ");
        scanner.nextLine();
        String email = getEmailInput("Enter email: ");
        String address = getAddressInput("Enter address: ");
        String password = getPasswordInput("Enter password: ");
        
        Learner learner = new Learner(library.GetNextStudentID(), firstName, lastName, middleName, gender, birthday, contactNum, email, address, password, library);
        
        // Add learner to block
        if (availableBlock.AddLearner(learner)) {
            library.AddLearner(learner);
            System.out.println("Learner added successfully.");
            System.out.println("Your new Student ID is: " + learner.GetStudentID());
            System.out.println("You have been assigned to Block " + availableBlock.GetBlockID());
        } else {
            System.out.println("Error: Could not add learner to block.");
        }
        
        System.out.println("Press enter to continue...");
        scanner.nextLine();
    }

    public void LearnerLogin() {
        ClearScreen();
        System.out.println("\nLearner Login");
        int studentID = getIntInput("Enter student ID: ");
        Learner learner = library.FindLearner(studentID);
        if (learner == null) {
            ClearScreenY();
            System.out.println("Learner not found.");
            return;
        }
        if (!library.IsLearnerInAnyBlock(learner)) {
            System.out.println("Error: You are not assigned to any block.");
            return;
        }
        LoggedInLearner = learner;
        System.out.println("Login successful.");
        setLoggedInLearner(learner);
        LearnerActionsMenu();
    }

    // Utility methods for input
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                if (scanner.hasNextInt()) {
                    return scanner.nextInt();
                } else {
                    System.out.println("Error: Input must be an integer.");
                    scanner.next(); // Clear the invalid input
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Input must be an integer.");
                scanner.next(); // Clear the invalid input
            } catch (NoSuchElementException e) {
                System.out.println("Error: Invalid input. Please try again.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private String getStringInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty() || !input.matches("[a-zA-Z\\s]+")) {
                        throw new IllegalArgumentException("Input must be a non-empty string containing only letters and spaces.");
                    }
                    return input;
                } else {
                    System.out.println("Error: No input available.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private String getEmailInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty() || !input.contains("@")) {
                        throw new IllegalArgumentException("Email must contain '@' and cannot be empty.");
                    }
                    return input;
                } else {
                    System.out.println("Error: No input available.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private String getAddressInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty() || !input.matches("[a-zA-Z0-9\\s]+")) {
                        throw new IllegalArgumentException("Address must be a non-empty alphanumeric string.");
                    }
                    return input;
                } else {
                    System.out.println("Error: No input available.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    private String getBirthdayInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    if (!input.matches("\\d{4}[-/]\\d{2}[-/]\\d{2}")) {
                        throw new IllegalArgumentException("Birthday must be in the format YYYY-MM-DD or YYYY/MM/DD.");
                    }
                    return input;
                } else {
                    System.out.println("Error: No input available.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private String getPasswordInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        throw new IllegalArgumentException("Password cannot be empty.");
                    }
                    return input;
                } else {
                    System.out.println("Error: No input available.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void ClearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private static void ClearScreenY() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // Fallback to printing fewer new lines if the escape sequence is not supported
        for (int i = 0; i < 1; i++) {
            System.out.println();
        }
    }
}