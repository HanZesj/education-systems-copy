package com.mycompany.mavenproject3;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FacultyFunctionsLearners {
    private final Library library;
    private final Scanner scanner;
    private Faculty loggedInFaculty;

    public FacultyFunctionsLearners(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

    public void SetLoggedInFaculty(Faculty faculty) {
        this.loggedInFaculty = faculty;
    }

    public void EditLearner() {
        clearScreen();
        if (loggedInFaculty == null || !loggedInFaculty.HasPrivileges()) {
            System.out.println("Access denied. No block assignment found.");
            return;
        }

        Block block = loggedInFaculty.GetAssignedBlock();
        List<Learner> learners = block.GetLearners();
        if (learners.isEmpty()) {            
            System.out.println("No learners in this block.");
            return;
        }
        ViewLearners();
        System.out.println("\nEdit Learner");
        int studentIDInt = getIntInput("Enter student ID: ");
        Learner learner = library.FindLearner(studentIDInt);
        if (learner == null) {
            System.out.println("Learner not found.");
            return;
        }
        scanner.nextLine();
        String firstName = getStringInput("Enter new first name: ");
        String lastName = getStringInput("Enter new last name: ");
        String middleName = getStringInput("Enter new middle name: ");
        String gender = getStringInput("Enter new gender: ");
        String birthday = getBirthdayInput("Enter new birthday (YYYY-MM-DD or YYYY/MM/DD): ");
        int contactNum = getIntInput("Enter new contact number: ");
        scanner.nextLine();
        String email = getEmailInput("Enter new email: ");
        String address = getAddressInput("Enter new address: ");

        learner.SetFirstName(firstName);
        learner.SetLastName(lastName);
        learner.SetMiddleName(middleName);
        learner.SetGender(gender);
        learner.SetBirthday(birthday);
        learner.SetContactNum(contactNum);
        learner.SetEmail(email);
        learner.SetAddress(address);
        System.out.println("Learner information updated successfully.");
        System.out.println("Press enter to continue...");
        scanner.nextLine(); 
    }

    public void DeleteLearner() {
        clearScreen();
        if (loggedInFaculty == null || !loggedInFaculty.HasPrivileges()) {
            System.out.println("Access denied. No block assignment found.");
            return;
        }

        Block block = loggedInFaculty.GetAssignedBlock();
        List<Learner> learners = block.GetLearners();
        if (learners.isEmpty()) {
            System.out.println("No learners in this block.");
            return;
        }
        ViewLearners();
        System.out.println("\nDelete Learner");
        int studentIDInt = getIntInput("Enter student ID: ");
        Learner learner = library.FindLearner(studentIDInt);
        if (learner == null) {
            System.out.println("Learner not found.");
            System.err.println("Press enter to continue...");
            scanner.nextLine(); // Clear the newline character
            return;
        }
        library.RemoveLearner(learner.GetStudentID());
        System.out.println("Learner deleted successfully.");
        System.err.println("Press enter to continue...");
        scanner.nextLine(); // Clear the newline character
    }

    public void SetLearnerViolations() {
        clearScreen();
        if (loggedInFaculty == null || !loggedInFaculty.HasPrivileges()) {
            System.out.println("Access denied. No block assignment found.");
            return;
        }

        Block block = loggedInFaculty.GetAssignedBlock();
        List<Learner> learners = block.GetLearners();
        if (learners.isEmpty()) {
            System.out.println("No learners in this block.");
            return;
        }
        ViewLearners();
        System.out.println("\nSet Learner Violations");
        int studentIDInt = getIntInput("Enter student ID: ");
        Learner learner = library.FindLearner(studentIDInt);
        if (learner == null) {
            System.out.println("Learner not found.");
            return;
        }
        int numberOfViolations = getIntInput("Enter number of violations: ");
        learner.SetNumberOfViolations(numberOfViolations);
        System.out.println("Number of violations set successfully.");
    }

    public void ViewLearners() {
        clearScreen();
        if (loggedInFaculty == null || !loggedInFaculty.HasPrivileges()) {
            System.out.println("Access denied. No block assignment found.");
            return;
        }

        Block block = loggedInFaculty.GetAssignedBlock();
        System.out.println("\nLearners in Block " + block.GetBlockID());
        List<Learner> learners = block.GetLearners();
        
        if (learners.isEmpty()) {
            System.out.println("No learners in this block.");
            return;
        }

        for (Learner learner : learners) {
            // Display learner details
            System.out.println(learner.toString());
        }
    }

    public void ManageLearners() {
        while (true) {
            clearScreen();
            System.out.println("---Manage Learners---");
            System.out.println("1. Edit Learner");
            System.out.println("2. Delete Learner");
            System.out.println("3. Set Learner Violations");
            System.out.println("4. View Learners");
            System.out.println("5. Back to Faculty Menu");
            
            int choice = getIntInput(":: ");
            scanner.nextLine(); // Clear buffer
            
            switch (choice) {
                case 1 -> EditLearner();
                case 2 -> DeleteLearner();
                case 3 -> SetLearnerViolations();
                case 4 -> ViewLearners();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Input must be an integer.");
                scanner.next(); 
            }
        }
    }

    private String getStringInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty() || !input.matches("[a-zA-Z\\s]+")) {
                    throw new IllegalArgumentException("Input must be a non-empty string containing only letters and spaces.");
                }
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private String getEmailInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty() || !input.contains("@")) {
                    throw new IllegalArgumentException("Email must contain '@' and cannot be empty.");
                }
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private String getBirthdayInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (!input.matches("\\d{4}[-/]\\d{2}[-/]\\d{2}")) {
                    throw new IllegalArgumentException("Birthday must be in the format YYYY-MM-DD or YYYY/MM/DD.");
                }
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private String getAddressInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty() || !input.matches("[a-zA-Z0-9\\s]+")) {
                    throw new IllegalArgumentException("Address must be a non-empty alphanumeric string.");
                }
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}