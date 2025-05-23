package com.mycompany.mavenproject3;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FacultyFunctionsMaterials {
    private final Library library;
    private final Scanner scanner;
    private Faculty loggedInFaculty;

    public FacultyFunctionsMaterials(Library library) {
        this.library = library;
        this.scanner = new Scanner(System.in);
    }

    public void SetLoggedInFaculty(Faculty faculty) {
        this.loggedInFaculty = faculty;
    }

    public void ManageMaterials() {
        while (true) {
            ClearScreen();
            System.out.println("---Manage Materials---");
            System.out.println("1. Add Material");
            System.out.println("2. Edit Material");
            System.out.println("3. Delete Material");
            System.out.println("4. View Materials");
            System.out.println("5. Back to Faculty Menu");
            
            int choice = getIntInput(":: ");
            scanner.nextLine(); // Clear buffer
            
            switch (choice) {
                case 1 -> AddMaterial();
                case 2 -> EditMaterial();
                case 3 -> DeleteMaterial();
                case 4 -> ViewMaterials();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void AddMaterial() {
        ClearScreen();
        if (loggedInFaculty == null || !loggedInFaculty.HasPrivileges()) {
            System.out.println("Access denied. No block assignment found.");
            return;
        }
        try {
            System.out.println("\nAdd Material");
            scanner.nextLine();
            String title = getStringInput("Enter title: ");
            String genre = getStringInput("Enter genre: ");
            String author = getStringInput("Enter author: ");
            String publisher = getStringInput("Enter publisher: ");
            int yearPublished = getIntInput("Enter year published: ");
            int copies = getIntInput("Enter number of copies: ");
            Material material = new Book(title, genre, author, publisher, yearPublished, copies);
            loggedInFaculty.AddMaterial(material);
            library.AddMaterial(material);
            System.out.println("Material added successfully with ID: " + material.GetMaterialID());
            System.err.println("Press enter to continue...");
            scanner.nextLine(); // Clear the newline character
        } catch (Exception e) {
            System.out.println("Error adding material: " + e.getMessage());
        }
    }

    public void EditMaterial() {
        ClearScreen();
        if (loggedInFaculty == null || !loggedInFaculty.HasPrivileges()) {
            System.out.println("Access denied. No block assignment found.");
            return;
        }
        try {
            List<Material> materials = library.GetMaterials();
            if (materials.isEmpty()) {
                System.out.println("No materials in the system yet.");
                return;
            }
            ViewMaterials();
            System.out.println("\nEdit Material");
            int materialID = getIntInput("Enter material ID: ");
            Material material = library.FindMaterial(materialID);
            if (material == null) {
                // ClearScreen();
                System.out.println("Material not found.");
                System.err.println("Press enter to continue...");
                scanner.nextLine(); // Clear the newline character
                return;
            }
            scanner.nextLine();
            String title = getStringInput("Enter title: ");
            String genre = getStringInput("Enter genre: ");
            String author = getStringInput("Enter author: ");
            String publisher = getStringInput("Enter publisher: ");
            int yearPublished = getIntInput("Enter year published: ");
            int copies = getIntInput("Enter number of copies: ");
            material.SetTitle(title);
            material.SetGenre(genre);
            material.SetAuthor(author);
            material.SetPublisher(publisher);
            material.SetYearPublished(yearPublished);
            material.SetCopies(copies);
            ClearScreen();
            System.out.println("Material edited successfully.");
            System.err.println("Press enter to continue...");
            scanner.nextLine(); // Clear the newline character
        } catch (Exception e) {
            System.out.println("Error editing material: " + e.getMessage());
        }
    }

    public void DeleteMaterial() {
        ClearScreen();
        if (loggedInFaculty == null || !loggedInFaculty.HasPrivileges()) {
            System.out.println("Access denied. No block assignment found.");
            return;
        }
        try {
            List<Material> materials = loggedInFaculty.GetAssignedMaterials();
            if (materials.isEmpty()) {
                System.out.println("No materials in your block to delete.");
                return;
            }
            ViewMaterials();
            System.out.println("\nDelete Material");
            int materialID = getIntInput("Enter material ID: ");
            Material material = library.FindMaterial(materialID);
            if (material == null || !materials.contains(material)) {
                // ClearScreen();
                System.out.println("Material not found in your block.");
                System.err.println("Press enter to continue...");
                scanner.nextLine(); // Clear the newline character
                return;
            }
            loggedInFaculty.RemoveMaterial(material);
            library.DeleteMaterial(materialID);
            ClearScreen();
            System.out.println("Material deleted successfully.");
            System.err.println("Press enter to continue...");
            scanner.nextLine(); // Clear the newline character
        } catch (Exception e) {
            System.out.println("Error deleting material: " + e.getMessage());
        }
    }

    public void ViewMaterials() {
        ClearScreen();
        if (loggedInFaculty == null || !loggedInFaculty.HasPrivileges()) {
            System.out.println("Access denied. No block assignment found.");
            return;
        }
        try { 
            System.out.println("\nMaterials in Block " + loggedInFaculty.GetAssignedBlock().GetBlockID());
            List<Material> materials = loggedInFaculty.GetAssignedMaterials();
            if (materials.isEmpty()) {
                System.out.println("No materials added to this block yet.");
                return;
            }
            for (Material material : materials) {
                // Display material details
                System.out.println(material.toString());
            }
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.out.println("Error viewing materials: " + e.getMessage());
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

    private static void ClearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
}