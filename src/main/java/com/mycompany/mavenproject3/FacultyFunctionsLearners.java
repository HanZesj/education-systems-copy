package com.mycompany.mavenproject3;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FacultyFunctionsLearners {
    private final Library library;
    private final Scanner scanner;

    public FacultyFunctionsLearners(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

    public void EditLearner() {
        clearScreen();
        List<Learner> learners = library.GetLearners();
        if (learners.isEmpty()) {            
            System.out.println("No learners in the system yet.");
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
        List<Learner> learners = library.GetLearners();
        if (learners.isEmpty()) {
            System.out.println("No learners in the system yet.");
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
        List<Learner> learners = library.GetLearners();
        if (learners.isEmpty()) {
            System.out.println("No learners in the system yet.");
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
        System.out.println("\nView Learners");
        List<Learner> learners = library.GetLearners();
        if (learners.isEmpty()) {
            System.out.println("No learners in the system yet.");
            return;
        }
        for (Learner learner : learners) {
            System.out.println("Student ID: " + learner.GetStudentID());
            System.out.println("Name: " + learner.GetFirstName() + " " + learner.GetLastName());
            System.out.println("Gender: " + learner.GetGender());
            System.out.println("Birthday: " + learner.GetBirthday());
            System.out.println("Contact Number: " + learner.GetContactNum());
            System.out.println("Email: " + learner.GetEmail());
            System.out.println("Address: " + learner.GetAddress());
            System.out.println("Number of Violations: " + learner.GetNumberOfViolations());
            for (String materialID : learner.GetBorrowedMaterials()) {
                Material material = library.FindMaterial(Integer.parseInt(materialID));
                if (material != null) {
                    System.out.println("  Borrowed Material ID: " + material.GetMaterialID() + ", Title: " + material.GetTitle());
                }
            }
            System.out.println();
        }
    }

    public void ManageLearners() {
        while (true) {
            System.out.println("\nManage Learners");
            System.out.println("1. Edit Learner");
            System.out.println("2. Delete Learner");
            System.out.println("3. View Learners");
            System.out.println("4. Set Learner Violations");
            System.out.println("5. Back to Main Menu");
            int choice = getIntInput(":: ");
            switch (choice) {
                case 1 -> EditLearner();
                case 2 -> DeleteLearner();
                case 3 -> ViewLearners();
                case 4 -> SetLearnerViolations();
                case 5 -> {
                    return; 
                }
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