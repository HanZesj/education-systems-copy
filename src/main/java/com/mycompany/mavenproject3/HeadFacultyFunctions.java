package com.mycompany.mavenproject3;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class HeadFacultyFunctions {
    private final Library library;
    private final Scanner scanner;
    private static final String HEAD_PASSWORD = "head123";

    public HeadFacultyFunctions(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

    public void HeadLogin() {
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (HEAD_PASSWORD.equals(password)) {
            HeadMenu();
        } else {
            System.out.println("Incorrect password.");
        }
    }

    private void HeadMenu() {
        while (true) {
            System.out.println("\n---Head Faculty Interface---");
            System.out.println("1. View Faculty Members");
            System.out.println("2. View Specific Faculty Member");
            System.out.println("3. Exit");
            int choice = getIntInput(":: ");
            switch (choice) {
                case 1 -> ViewFacultyMembers();
                // case 2 -> ViewSpecificFacultyMember();
                case 3 -> {
                    return; // Exit the HeadMenu loop
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void ViewFacultyMembers() {
        List<Faculty> facultyMembers = library.GetFacultyMembers();
        if (facultyMembers.isEmpty()) {
            System.out.println("No faculty members in the system yet.");
            return;
        }
        System.out.println("\nFaculty Members:");
        for (Faculty faculty : facultyMembers) {
            System.out.println("ID: " + faculty.GetFacultyID() + ", Name: " + faculty.GetFullName());
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Input must be an integer.");
                scanner.next(); // Clear the invalid input
            }
        }
    }
}