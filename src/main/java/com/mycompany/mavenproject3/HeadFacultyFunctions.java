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
            System.out.println("1. View All Faculty Members");
            System.out.println("2. View Faculty Assignments");
            System.out.println("3. Assign Faculty to Block");
            System.out.println("4. Remove Faculty from Block");
            System.out.println("5. Delete Faculty Member");
            System.out.println("6. Exit");
            
            int choice = getIntInput(":: ");
            switch (choice) {
                case 1 -> ViewFacultyMembers();
                case 2 -> ViewFacultyAssignments();
                case 3 -> AssignFacultyToBlock();
                case 4 -> RemoveFacultyFromBlock();
                case 5 -> DeleteFacultyMember();
                case 6 -> {
                    return;
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

    private void ViewFacultyAssignments() {
        System.out.println("\nCurrent Faculty Assignments:");
        List<Block> blocks = library.GetBlocks();
        for (Block block : blocks) {
            System.out.println("\nBlock " + block.GetBlockID() + ":");
            Faculty assigned = block.GetAssignedFaculty();
            if (assigned != null) {
                System.out.println("Assigned Faculty: " + assigned.GetFullName() + " (ID: " + assigned.GetFacultyID() + ")");
            } else {
                System.out.println("No faculty assigned");
            }
        }
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void AssignFacultyToBlock() {
        ViewFacultyMembers();
        System.out.println("\nAssign Faculty to Block");
        
        int facultyID = getIntInput("Enter Faculty ID: ");
        Faculty faculty = library.FindFacultyByID(facultyID);
        
        if (faculty == null) {
            System.out.println("Faculty member not found.");
            return;
        }

        System.out.println("\nAvailable Blocks:");
        List<Block> blocks = library.GetBlocks();
        for (Block block : blocks) {
            System.out.println("Block " + block.GetBlockID() + 
                (block.HasFaculty() ? " (Occupied)" : " (Available)"));
        }

        int blockID = getIntInput("Enter Block ID: ");
        Block block = library.FindBlock(blockID);
        
        if (block == null) {
            System.out.println("Block not found.");
            return;
        }

        if (block.HasFaculty()) {
            System.out.println("This block already has an assigned faculty member.");
            return;
        }

        block.SetFaculty(faculty);
        System.out.println("Faculty successfully assigned to block.");
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void RemoveFacultyFromBlock() {
        ViewFacultyAssignments();
        System.out.println("\nRemove Faculty from Block");
        
        int blockID = getIntInput("Enter Block ID: ");
        Block block = library.FindBlock(blockID);
        
        if (block == null) {
            System.out.println("Block not found.");
            return;
        }

        if (!block.HasFaculty()) {
            System.out.println("This block has no faculty assigned.");
            return;
        }

        block.SetFaculty(null);
        System.out.println("Faculty successfully removed from block.");
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void DeleteFacultyMember() {
        ViewFacultyMembers();
        System.out.println("\nDelete Faculty Member");
        
        int facultyID = getIntInput("Enter Faculty ID to delete: ");
        Faculty faculty = library.FindFacultyByID(facultyID);
        
        if (faculty == null) {
            System.out.println("Faculty member not found.");
            return;
        }

        // Remove faculty from any assigned block
        for (Block block : library.GetBlocks()) {
            if (block.GetAssignedFaculty() != null && 
                block.GetAssignedFaculty().GetFacultyID() == facultyID) {
                block.SetFaculty(null);
            }
        }

        library.RemoveFaculty(facultyID);
        System.out.println("Faculty member successfully deleted.");
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
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