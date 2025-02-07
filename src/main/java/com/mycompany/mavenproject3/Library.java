package com.mycompany.mavenproject3;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private final List<Material> materials;
    private final List<Learner> learners;
    private final List<Faculty> facultyMembers;
    private final List<Block> blocks;
    private int nextFacultyID;
    private int nextBlockID = 1;

    public Library() {
        this.materials = new ArrayList<>();
        this.learners = new ArrayList<>();
        this.facultyMembers = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.nextFacultyID = 1000;
        // Add 5 blocks with default capacity of 40 students each
        for(int i = 1; i <= 5; i++) {
            blocks.add(new Block(i, 40));  // Changed to include capacity parameter
        }
    }

    // Material-related methods
    public void AddMaterial(Material material) {
        materials.add(material);
    }

    public void DeleteMaterial(int materialID) {
        materials.removeIf(material -> material.GetMaterialID() == materialID);
    }

    public List<Material> GetMaterials() {
        return materials;
    }

    public Material FindMaterial(int materialID) {
        for (Material material : materials) {
            if (material.GetMaterialID() == materialID) {
                return material;
            }
        }
        return null;
    }

    // Learner-related methods
    public void AddLearner(Learner learner) {
        learners.add(learner);
    }

    public void RemoveLearner(int studentID) {
        learners.removeIf(learner -> learner.GetStudentID() == studentID);
    }

    public List<Learner> GetLearners() {
        return learners;
    }

    public Learner FindLearner(int studentID) {
        for (Learner learner : learners) {
            if (learner.GetStudentID() == studentID) {
                return learner;
            }
        }
        return null;
    }

    public int GetNextStudentID() {
        int lastStudentID = 0;
        for (Learner learner : learners) {
            if (learner.GetStudentID() > lastStudentID) {
                lastStudentID = learner.GetStudentID();
            }
        }
        return lastStudentID + 1;
    }

    // faculty member-related methods
    public void AddFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Faculty cannot be null");
        }
        facultyMembers.add(faculty);
    }

    public void RemoveFaculty(int facultyID) {
        facultyMembers.removeIf(faculty -> faculty.GetFacultyID() == facultyID);
    }

    public List<Faculty> GetFacultyMembers() {
        return facultyMembers;
    }

    public int GetNextFacultyID(){
        return nextFacultyID++;
    }

    public Faculty FindFaculty(int facultyID, String password) {
        if (password == null || password.isEmpty()) {
            System.out.println("Invalid password");
            return null;
        }
        
        for (Faculty faculty : facultyMembers) {
            if (faculty.GetFacultyID() == facultyID) {
                if (faculty.GetPassword().equals(password)) {
                    return faculty;
                } else {
                    System.out.println("Invalid password");
                    return null;
                }
            }
        }
        System.out.println("Faculty ID not found");
        return null;
    }

    public Faculty FindFacultyByID(int facultyID) {
        for (Faculty faculty : facultyMembers) {
            if (faculty.GetFacultyID() == facultyID) {
                return faculty;
            }
        }
        return null;
    }

    // Block-related methods
    public List<Block> GetBlocks() {
        return blocks;
    }

    public Block FindBlock(int blockID) {
        for (Block block : blocks) {
            if (block.GetBlockID() == blockID) {
                return block;
            }
        }
        return null;
    }

    public Block GetAvailableBlock() {
        for (Block block : blocks) {
            if (!block.IsFull()) {
                return block;
            }
        }
        return null;
    }

    public boolean IsLearnerInAnyBlock(Learner learner) {
        for (Block block : blocks) {
            if (block.HasLearner(learner)) {
                return true;
            }
        }
        return false;
    }
}