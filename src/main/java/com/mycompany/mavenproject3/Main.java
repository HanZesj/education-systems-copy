package com.mycompany.mavenproject3;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static Library library;
    private static LearnerFunctions learnerFunctions;
    private static FacultyFunctionsMaterials facultyFunctionsMaterials;
    private static FacultyFunctionsLearners facultyFunctionsLearners;
    private static FacultyFunctions facultyFunctions;
    private static Learner LoggedInLearner;
    private static HeadFacultyFunctions headFacultyFunctions;

    public static void main(String args[]) {
        initializeComponents();
        runMainLoop();
    }

    private static void initializeComponents() {
        scanner = new Scanner(System.in);
        library = new Library();
        headFacultyFunctions = new HeadFacultyFunctions(library);
        learnerFunctions = new LearnerFunctions(library);
        facultyFunctionsMaterials = new FacultyFunctionsMaterials(library);
        facultyFunctionsLearners = new FacultyFunctionsLearners(library);
        facultyFunctions = new FacultyFunctions(library);
    }

    private static void runMainLoop() {
        while (true) {
            ClearScreen();
            System.out.println("Welcome to the Library Management System!");
            System.out.println("1. Head Faculty Login");
            System.out.println("2. Faculty Interface");
            System.out.println("3. Learner Interface");
            System.out.println("4. Exit");
            int choice = getIntInput(":: ");
            switch (choice) {
                case 1 -> headFacultyFunctions.HeadLogin();
                case 2 -> FacultyMenu();
                case 3 -> LearnerMenu();
                case 4 -> {
                    System.out.println("Exiting the program.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Methods for faculty menu
    private static void FacultyMenu() {
        ClearScreen();
        while (true) {
            System.out.println("---Faculty Interface---");
            System.out.println("1. Login.");
            System.out.println("2. Don't have an account? Register now");
            System.out.println("3. Back to Main Menu.");
            int choice = getIntInput(":: ");
            switch (choice) {
                case 1 -> facultyFunctions.FacultyLogin();
                case 2 -> facultyFunctions.RegisterFaculty();
                case 3 -> {
                    return; // Exit the FacultyMenu loop
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Methods for learner menu
    private static void LearnerMenu() {
        ClearScreen();
        while (true) {
            System.out.println("---Learner Interface---");
            System.out.println("1. Login");
            System.out.println("2. Don't have an account? Register now.");
            System.out.println("3. Back to Main Menu.");
            int choice = getIntInput(":: ");
            switch (choice) {
                case 1 -> learnerFunctions.LearnerLogin();
                case 2 -> learnerFunctions.RegisterLearner();
                case 3 -> {
                    return; // Exit the LearnerMenu loop
                }
                default -> System.out.println("Invalid choice. Please Select a valid option.");
            }
        }
    }

    // Utility methods for input
    private static int getIntInput(String prompt) {
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

    public static String GetStringInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine().trim();
                    if (!input.matches("[a-zA-Z\\s]+") && !input.isEmpty()) {
                        System.out.println("Input must be a string. Please try again.");
                    }
                    return input;
                } else {
                    System.out.println("Error: No input available.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
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