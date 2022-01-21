package com.foxminded.school;

import com.foxminded.course.Course;
import com.foxminded.datasource.DataSource;
import com.foxminded.services.courseservices.CourseServices;
import com.foxminded.services.groupservices.GroupServices;
import com.foxminded.services.studentservices.StudentServices;
import com.foxminded.stringformatter.StringFormatter;
import com.foxminded.student.Student;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class School {
    public static void main(String[] args) throws SQLException, IOException {
        DataSource dataSource = new DataSource();
        createTable(dataSource);
        CourseServices courseServices = new CourseServices();
        StudentServices studentServices = new StudentServices();
        GroupServices groupServices = new GroupServices();
        StringFormatter stringFormatter = new StringFormatter();

        System.out.println("1-Find all groups with fewer or fewer students\n" +
                "2-Find all students who are relevant to the course with the given names\n" +
                "3-Add a new student\n" +
                "4-Remove student by STUDENT_ID\n" +
                "5-Add a student to the course\n" +
                "6-Exclude a student from one of his courses");
        Scanner scanner = new Scanner(System.in);
        String input = null;
        int select = 0;
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
                studentServices.deleteCourseForStudent(idStudent,Integer.parseInt(input));
                break;
            case 6:
                stringFormatter.allListStudent();
                stringFormatter.allListCourse();
                System.out.println("Enter Student ID:");
                input = scanner.next();
                int idStudentAdd = Integer.parseInt(input);
                System.out.println("Enter Course ID:");
                input = scanner.next();
                studentServices.addStudentForCourse(idStudentAdd,Integer.parseInt(input));
                break;
            default:
                System.out.println("Incorrect data entered");
                deleteTable(dataSource);
        }
    }
    public static void createTable(DataSource dataSource) throws SQLException, IOException {
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableGroups.sql"));
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableStudents.sql"));
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableCourses.sql"));
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableStudentsCourses.sql"));
    }

    public static void deleteTable(DataSource dataSource) throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE studentsCourses;");
        statement.execute("DROP TABLE students;");
        statement.execute("DROP TABLE groups;");
        statement.execute("DROP TABLE courses;");
    }
}
