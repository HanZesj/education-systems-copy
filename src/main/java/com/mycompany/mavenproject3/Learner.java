package com.mycompany.mavenproject3;

import java.util.ArrayList;
import java.util.List;

public class Learner {
    private int studentID;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private String birthday;
    private int contactNum;
    private String email;
    private String address;
    private int numberOfViolations;
    private final List<String> borrowedMaterials;
    private final LearnerFunctions learnerFunctions;

    public Learner(int studentID, String firstName, String lastName, String middleName, String gender, String birthday, int contactNum, String email, String address, Library library) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthday = birthday;
        this.contactNum = contactNum;
        this.email = email;
        this.address = address;
        this.numberOfViolations = 0;
        this.borrowedMaterials = new ArrayList<>();
        this.learnerFunctions = new LearnerFunctions(library);
    }

    public int GetStudentID() {
        return studentID;
    }

    public void SetStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String GetFirstName() {
        return firstName;
    }

    public void SetFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String GetLastName() {
        return lastName;
    }

    public void SetLastName(String lastName) {
        this.lastName = lastName;
    }

    public String GetMiddleName() {
        return middleName;
    }

    public void SetMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String GetGender() {
        return gender;
    }

    public void SetGender(String gender) {
        this.gender = gender;
    }

    public String GetBirthday() {
        return birthday;
    }

    public void SetBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int GetContactNum() {
        return contactNum;
    }

    public void SetContactNum(int contactNum) {
        this.contactNum = contactNum;
    }

    public String GetEmail() {
        return email;
    }

    public void SetEmail(String email) {
        this.email = email;
    }

    public String GetAddress() {
        return address;
    }

    public void SetAddress(String address) {
        this.address = address;
    }

    public int GetNumberOfViolations() {
        return numberOfViolations;
    }

    public void SetNumberOfViolations(int numberOfViolations) {
        this.numberOfViolations = numberOfViolations;
    }

    public List<String> GetBorrowedMaterials() {
        return borrowedMaterials;
    }

    public void BorrowMaterial(int materialID) {
        borrowedMaterials.add(String.valueOf(materialID));
    }

    public void ReturnMaterial(int materialID) {
        borrowedMaterials.remove(String.valueOf(materialID));
    }

    public void ViewAvailableBooks() {
        learnerFunctions.ViewAvailableBooks();
    }

    public void BorrowBook(int materialID) {
        learnerFunctions.BorrowBook();
    }

    public void ReturnBook(int materialID) {
        learnerFunctions.ReturnBook();
    }

    public void ViewBorrowedBooks() {
        learnerFunctions.ViewBorrowedBooks();
    }

    public void ViewViolations() {
        learnerFunctions.ViewViolations();
    }

    public void ViewLearnerInformation() {
        learnerFunctions.ViewLearnerInformation();
    }
}