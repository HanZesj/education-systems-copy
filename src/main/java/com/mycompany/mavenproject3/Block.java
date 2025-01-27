package com.mycompany.mavenproject3;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private final int blockID;
    private final int capacity;
    private Faculty assignedFaculty;
    private final List<Object> learners; // Using Object as placeholder for Learner class

    public Block(int blockID, int capacity) {
        this.blockID = blockID;
        this.capacity = capacity;
        this.learners = new ArrayList<>();
    }

    public int GetBlockID() {
        return blockID;
    }

    public int GetCapacity() {
        return capacity;
    }

    public List<Object> GetLearners() {
        return learners;
    }

    public boolean HasFaculty() {
        return assignedFaculty != null;
    }

    public void SetFaculty(Faculty faculty) {
        this.assignedFaculty = faculty;
    }
}
