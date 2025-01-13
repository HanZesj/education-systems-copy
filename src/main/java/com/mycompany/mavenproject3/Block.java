package com.mycompany.mavenproject3;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private final int blockID;
    private final int capacity = 40;
    private final List<Learner> learners;
    private Faculty faculty;

    public Block(int blockID) {
        this.blockID = blockID;
        this.learners = new ArrayList<>();
    }

    // this view function lets the faculty members view what blocks are available and what blocks are occupied
    public void ViewAvailableBlocksFaculty(){}

    // this view function lets the learners have a feedback on what block are full and not full yet
    public void ViewAvailableBlocksLearner(){}

    public int GetBlockID() {
        return blockID;
    }

    public int GetCapacity() {
        return capacity;
    }

    public List<Learner> GetLearners() {
        return learners;
    }

    public void AddLearner(Learner learner) {
        learners.add(learner);
    }

    public void RemoveLearner(int studentID) {
        learners.removeIf(learner -> learner.GetStudentID() == studentID);
    }

    public Faculty GetFaculty() {
        return faculty;
    }

    public void SetFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void RemoveFaculty() {
        faculty = null;
    }

    public boolean IsFull() {
        return learners.size() == capacity;
    }

    public boolean IsEmpty() {
        return learners.isEmpty();
    }

    public boolean HasFaculty() {
        return faculty != null;
    }

    public boolean HasLearner(int studentID) {
        for (Learner learner : learners) {
            if (learner.GetStudentID() == studentID) {
                return true;
            }
        }
        return false;
    }

    public Learner FindLearner(int studentID) {
        for (Learner learner : learners) {
            if (learner.GetStudentID() == studentID) {
                return learner;
            }
        }
        return null;
    }
}
