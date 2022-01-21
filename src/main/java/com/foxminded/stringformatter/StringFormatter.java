package com.foxminded.stringformatter;

import com.foxminded.services.courseservices.CourseServices;
import com.foxminded.services.groupservices.GroupServices;
import com.foxminded.services.studentservices.StudentServices;
import com.foxminded.student.Student;

import java.util.List;
import java.util.stream.Collectors;

public class StringFormatter {

    private GroupServices groupServices = new GroupServices();
    private StudentServices studentServices = new StudentServices();
    private CourseServices courseServices = new CourseServices();

    public String formatterFindGroupsByNumberOfStudents(int numberStudent) {
        return groupServices.findGroupsByNumberOfStudents(numberStudent).stream()
                .map(p -> p.getGroupId() + " " + p.getGroupName())
                .collect(Collectors.joining());
    }

    public String formatterStudentsByCourse(String namedCourse) {
        List<Student> studentList = studentServices.findStudentsByCourse(namedCourse);
        int maxLastName = calculateMaxLastNameStudent(studentList);
        int maxFirstName = calculateMaxFirstNameStudent(studentList);
        int maxId = calculateMaxIdStudent(studentList);
        return studentList.stream()
                .map(p -> formatterStudent(p,maxId,maxFirstName,maxLastName))
                .collect(Collectors.joining("\n"));
    }

    private int calculateMaxFirstNameStudent(List<Student> studentList) {
        return studentList.stream()
                .map(p -> p.getFirstName().length())
                .max(Integer::compare)
                .get();
    }

    private int calculateMaxLastNameStudent(List<Student> studentList) {
        return studentList.stream()
                .map(p -> p.getLastName().length())
                .max(Integer::compare)
                .get();
    }

    private int calculateMaxIdStudent(List<Student> studentList) {
        return studentList.stream()
                .map(p -> String.valueOf(p.getStudentId()).length())
                .max(Integer::compare)
                .get();
    }

    private String formatterStudent(Student student , int maxId , int maxFirstName , int maxLastName) {
        return String.format("%-" + maxId + "d.%-" + maxFirstName + "s%-" + maxLastName + "%s %s",student.getStudentId(),student.getFirstName(),student.getLastName(),student.getGroupId());
    }

    public String allListCourse() {
        return courseServices.getAllCourse().stream()
                .map(p -> p.getCourseId() + "  " + p.getCourseName())
                .collect(Collectors.joining("\n"));
    }

    public String allListStudent() {
        List<Student> studentList = studentServices.getAllStudent();
        int maxLastName = calculateMaxLastNameStudent(studentList);
        int maxFirstName = calculateMaxFirstNameStudent(studentList);
        int maxId = calculateMaxIdStudent(studentList);
        return studentList.stream()
                .map(p -> formatterStudent(p,maxId,maxFirstName,maxLastName))
                .collect(Collectors.joining("\n"));
    }
}
