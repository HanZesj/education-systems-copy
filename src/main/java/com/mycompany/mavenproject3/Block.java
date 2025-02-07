package com.mycompany.mavenproject3;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private final int blockID;
    private final int capacity;
    private Faculty assignedFaculty;
    private final List<Learner> learners;

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

    public List<Learner> GetLearners() {
        return learners;
    }

    public boolean HasFaculty() {
        return assignedFaculty != null;
    }

    public void SetFaculty(Faculty faculty) {
        this.assignedFaculty = faculty;
    }

    public boolean IsFull() {
        return learners.size() >= capacity;
    }

    public boolean AddLearner(Learner learner) {
        if (IsFull()) {
            return false;
        }
        learners.add(learner);
        return true;
    }

    public boolean HasLearner(Learner learner) {
        return learners.contains(learner);
    }

    public Faculty GetAssignedFaculty() {
        return assignedFaculty;
    }
}
