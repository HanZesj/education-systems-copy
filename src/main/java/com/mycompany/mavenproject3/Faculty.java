package com.mycompany.mavenproject3;

public class Faculty {
    private final FacultyFunctionsMaterials facultyFunctionsMaterials;
    private final FacultyFunctionsLearners facultyFunctionsLearners;

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

    public Faculty(int facultyID, String firstName, String lastName, String middleName, String gender, String birthday, int contactNum, String email, String address, String password, Library library) {
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
        this.assignedBlock = null;
        this.facultyFunctionsMaterials = new FacultyFunctionsMaterials(library);
        this.facultyFunctionsLearners = new FacultyFunctionsLearners(library);
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

    public void AddMaterial() {
        facultyFunctionsMaterials.AddMaterial();
    }

    public void DeleteMaterial() {
        facultyFunctionsMaterials.DeleteMaterial();
    }

    public void ViewMaterials() {
        facultyFunctionsMaterials.ViewMaterials();
    }

    public void EditLearner() {
        facultyFunctionsLearners.EditLearner();
    }

    public void DeleteLearner() {
        facultyFunctionsLearners.DeleteLearner();
    }

    public void ViewLearners() {
        facultyFunctionsLearners.ViewLearners();
    }

    public void SetLearnerViolations() {
        facultyFunctionsLearners.SetLearnerViolations();
    }

    public void ManageLearners() {
        facultyFunctionsLearners.ManageLearners();
    }
}