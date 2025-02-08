package com.mycompany.mavenproject3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException; 
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FacultyFunctions {
    private static FacultyFunctionsLearners manageLearners;
    private final Scanner scanner;
    private static FacultyFunctionsMaterials facultyFunctionsMaterials;
    private final Library library;
    private Faculty loggedInFaculty;

    public FacultyFunctions(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
        manageLearners = new FacultyFunctionsLearners(library);
        facultyFunctionsMaterials = new FacultyFunctionsMaterials(library);
    }

    private void FacultyActionsMenu() {
        if (loggedInFaculty == null) {
            System.out.println("Error: No faculty logged in");
            return;
        }

        // Pass logged in faculty to the management classes
        manageLearners.SetLoggedInFaculty(loggedInFaculty);
        facultyFunctionsMaterials.SetLoggedInFaculty(loggedInFaculty);

        ClearScreen();
        while (true) {
            try {
                System.out.println("---Faculty Interface---");
                if (!loggedInFaculty.HasPrivileges()) {
                    System.out.println("\nNOTICE: Your account has limited access.");
                    System.out.println("Please consult the Head Faculty to be assigned to a block");
                    System.out.println("and gain full privileges.\n");
                }
                System.out.println("1. Manage Learners");
                System.out.println("2. Manage Materials");
                System.out.println("3. Back to Main Menu");
                
                int choice = getIntInput(":: ");
                switch (choice) {
                    case 1 -> {
                        if (!loggedInFaculty.HasPrivileges()) {
                            System.out.println("Access denied. Please consult Head Faculty to be assigned to a block first.");
                            System.out.println("Press Enter to continue...");
                            scanner.nextLine();
                        } else if (manageLearners != null) {
                            manageLearners.ManageLearners();
                        }
                    }
                    case 2 -> {
                        if (!loggedInFaculty.HasPrivileges()) {
                            System.out.println("Access denied. Please consult Head Faculty to be assigned to a block first.");
                            System.out.println("Press Enter to continue...");
                            scanner.nextLine();
                        } else if (facultyFunctionsMaterials != null) {
                            facultyFunctionsMaterials.ManageMaterials();
                        }
                    }
                    case 3 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error in faculty menu: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void RegisterFaculty() {
        ClearScreen();
        try {
            System.out.println("\nAdd Faculty");
            String firstName = getStringInput("Enter first name: ", "[a-zA-Z\\s]+", "Input must be a string.");
            String lastName = getStringInput("Enter last name: ", "[a-zA-Z\\s]+", "Input must be a string.");
            String middleName = getStringInput("Enter middle name: ", "[a-zA-Z\\s]+", "Input must be a string.");
            String gender = getStringInput("Enter gender: ", "[a-zA-Z\\s]+", "Input must be a string.");
            LocalDate birthday = getBirthDateInput("Enter birthday (YYYY-MM-DD): ");
            int contactNum = getIntInput("Enter contact number: ");
            scanner.nextLine(); // Clear buffer after numeric input
            String email = getEmailInput("Enter email: ");
            String address = getAddressInput("Enter address: ");
            String password = getPasswordInput("Enter password: ");
            
            Faculty faculty = new Faculty(library.GetNextFacultyID(), firstName, lastName, middleName, gender, 
            birthday.toString(), contactNum, email, address, password, library);
            library.AddFaculty(faculty);
            
            System.out.println("Faculty added successfully.");
            System.out.println("Your new Faculty ID is: " + faculty.GetFacultyID());
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            ClearScreen();
        } catch (Exception e) {
            System.out.println("Error adding faculty: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void FacultyLogin() {
        try {
            ClearScreen();
            System.out.println("\nFaculty Login");
            int inputFacultyID = getIntInput("Enter Faculty ID: ");
            scanner.nextLine(); // Clear buffer after numeric input
            String inputPassword = getStringInput("Enter password: ", ".+", "Password cannot be empty.");
            
            Faculty faculty = library.FindFaculty(inputFacultyID, inputPassword);
            if (faculty == null) {
                System.out.println("Invalid username or password.");
                System.out.println("Press enter to continue...");
                scanner.nextLine();
                return;
            }
            
            loggedInFaculty = faculty;
            System.out.println("Login successful.");
            System.out.println("Press enter to continue...");
            scanner.nextLine();
            FacultyActionsMenu();
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void AssignBlockToFaculty(Faculty faculty) {
        System.out.println("Select a block to marshal:");
        List<Block> blocks = library.GetBlocks();
        for (Block block : blocks) {
            System.out.println("Block ID: " + block.GetBlockID() + ", Capacity: " + block.GetCapacity() + ", Assigned Learners: " + block.GetLearners().size() + ", Occupied: " + block.HasFaculty());
        }
        int blockID = getIntInput("Enter block ID: ");
        Block block = library.FindBlock(blockID);
        if (block != null && !block.HasFaculty()) {
            block.SetFaculty(faculty);
            faculty.SetAssignedBlock(block);
            System.out.println("Block assigned successfully.");
        } else {
            System.out.println("Block assignment failed. Please try again.");
        }
    }

    private LocalDate getBirthDateInput(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            try {
                System.out.print(prompt);
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    LocalDate date = LocalDate.parse(input, formatter);
                    if (date.isAfter(LocalDate.now())) {
                        throw new IllegalArgumentException("Date cannot be in the future.");
                    }
                    return date;
                } else {
                    System.out.println("Error: No input available.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error: Date must be in the format YYYY-MM-DD.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

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

    private String getStringInput(String prompt, String pattern, String errorMessage) {
        while (true) {
            try {
                System.out.print(prompt);
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    if (!input.matches(pattern)) {
                        throw new IllegalArgumentException(errorMessage);
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

    private String getEmailInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty() || !input.contains("@")) {
                        throw new IllegalArgumentException("Email must contain '@'.");
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

    private static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}