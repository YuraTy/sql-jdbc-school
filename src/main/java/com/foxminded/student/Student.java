package com.foxminded.student;

public class Student {

    private String firstName;
    private String lastName;
    private int groupId;
    private int studentId;

    public Student(String firstName, String lastName, int groupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
    }

    public Student(int studentId, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
    }

    public Student(){}

    public Student(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return firstName.equals(student.firstName) && lastName.equals(student.lastName);
    }
}
