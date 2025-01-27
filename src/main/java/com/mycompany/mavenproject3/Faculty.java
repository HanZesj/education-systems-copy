package com.mycompany.mavenproject3;

public class Faculty {
    private final int facultyID;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String gender;
    private final String birthday;
    private final int contactNum;
    private final String email;
    private final String address;
    private final String password;
    private Block assignedBlock;
    private final Library library;

    public Faculty(int facultyID, String firstName, String lastName, String middleName, 
                  String gender, String birthday, int contactNum, String email, 
                  String address, String password, Library library) {
        this.facultyID = facultyID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthday = birthday;
        this.contactNum = contactNum;
        this.email = email;
        this.address = address;
        this.password = password;
        this.library = library;
    }

    public int GetFacultyID() {
        return facultyID;
    }

    public String GetFirstName() {
        return firstName;
    }

    public String GetLastName() {
        return lastName;
    }

    public String GetMiddleName() {
        return middleName;
    }

    public String GetGender() {
        return gender;
    }

    public String GetBirthday() {
        return birthday;
    }

    public int GetContactNum() {
        return contactNum;
    }

    public String GetEmail() {
        return email;
    }

    public String GetAddress() {
        return address;
    }

    public String GetPassword() {
        return password;
    }

    public Block GetAssignedBlock() {
        return assignedBlock;
    }

    public void SetAssignedBlock(Block assignedBlock) {
        this.assignedBlock = assignedBlock;
    }

    public String GetFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public boolean CheckPassword(String password) {
        return this.password.equals(password);
    }
}