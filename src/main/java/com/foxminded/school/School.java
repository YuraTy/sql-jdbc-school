package com.foxminded.school;

import com.foxminded.course.Course;
import com.foxminded.services.courseservices.CourseServices;
import com.foxminded.services.groupservices.GroupServices;
import com.foxminded.services.studentservices.StudentServices;
import com.foxminded.stringformatter.StringFormatter;
import com.foxminded.student.Student;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class School {
    public static void main(String[] args) throws SQLException, IOException {
        StudentServices studentServices = new StudentServices();
        GroupServices groupServices = new GroupServices();
        CourseServices courseServices = new CourseServices();
        StringFormatter stringFormatter = new StringFormatter();

        studentServices.createTableStudents();
        groupServices.createTableGroup();
        courseServices.createTableCourses();
        studentServices.createTableCourseStudent();

        groupServices.fillingGroupsDB();
        studentServices.fillingStudentsDB();
        courseServices.fillingCoursesDB();
        courseServices.fillingStudentsCourses();

        System.out.println("1-Find all groups with fewer or fewer students\n" +
                "2-Find all students who are relevant to the course with the given names\n" +
                "3-Add a new student\n" +
                "4-Remove student by STUDENT_ID\n" +
                "5-Add a student to the course\n" +
                "6-Exclude a student from one of his courses");
        Scanner scanner = new Scanner(System.in);
        String input = null;
        int select = 0;
        input = scanner.next();
        try {
            select = Integer.parseInt(input);
        } catch (NumberFormatException e){
            System.out.println("Invalid input");
        }
        switch (select){
            case 1:
                System.out.println("Enter number of students:");
                input = scanner.next();
                System.out.println(stringFormatter.formatterFindGroupsByNumberOfStudents(Integer.parseInt(input)));
                break;
            case 2:
                System.out.println("Enter course name:");
                input = scanner.next();
                System.out.println(stringFormatter.formatterStudentsByCourse(input));
                break;
            case 3:
                System.out.println("Add new student");
                System.out.println("Enter student name:");
                Student student = new Student();
                input = scanner.next();
                student.setFirstName(input);
                System.out.println("Enter the student's last name:");
                input = scanner.next();
                student.setLastName(input);
                System.out.println("Enter student group Id:");
                input = scanner.next();
                student.setGroupId(Integer.parseInt(input));
                studentServices.newStudent(student);
                break;
            case 4:
                System.out.println("Enter the student ID to delete:");
                input = scanner.next();
                studentServices.deleteStudent(Integer.parseInt(input));
                break;
            case 5:
                System.out.println("Enter Student ID:");
                input = scanner.next();
                int idStudent = Integer.parseInt(input);
                System.out.println("Enter Course ID:");
                input = scanner.next();
                studentServices.deleteCourseForStudent(new Student(idStudent),new Course(Integer.parseInt(input)));
                break;
            case 6:
                System.out.println(stringFormatter.allListStudent());
                System.out.println(stringFormatter.allListCourse());

                System.out.println("Enter Student ID:");
                input = scanner.next();
                int idStudentAdd = Integer.parseInt(input);
                System.out.println("Enter Course ID:");
                input = scanner.next();
                studentServices.addStudentForCourse(new Student(idStudentAdd),new Course(Integer.parseInt(input)));
                break;
            default:
                System.out.println("Incorrect data entered");
                studentServices.deleteTableCourseStudent();
                studentServices.deleteTableStudents();
                groupServices.deleteTableGroup();
                courseServices.deleteTableCourses();
        }
        studentServices.deleteTableCourseStudent();
        studentServices.deleteTableStudents();
        groupServices.deleteTableGroup();
        courseServices.deleteTableCourses();
    }
}
