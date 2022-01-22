package com.foxminded.dao.studentdao;

import com.foxminded.course.Course;
import com.foxminded.datasource.DataSource;
import com.foxminded.student.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private final DataSource dataSource = new DataSource();

    @Override
    public void create(Student student) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Students SET FirstName=? , LastName=? , GroupId=? ")) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getGroupId());
            preparedStatement.execute();
            System.out.println("Data recorded");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> studentsList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT StudentId , GroupId , FirstName , LastName FROM Students");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("StudentId"));
                student.setGroupId(resultSet.getInt("GroupId"));
                student.setFirstName(resultSet.getString("FirstName"));
                student.setLastName(resultSet.getString("LastName"));
                studentsList.add(student);
            }
            return studentsList;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return studentsList;
    }

    @Override
    public Student findId(int studentId) {
        Student student = new Student();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT StudentId , GroupId , FirstName , LastName FROM Students WHERE StudentId=?")) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student.setStudentId(resultSet.getInt("StudentId"));
                student.setGroupId(resultSet.getInt("GroupId"));
                student.setFirstName(resultSet.getString("FirstName"));
                student.setLastName(resultSet.getString("LastName"));
            }
            return student;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void update(Student student, int studentId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateById = connection.prepareStatement("UPDATE Students SET GroupId = ? , FirstName = ? , LastName = ? WHERE StudentId = ?")) {
            updateById.setInt(1, student.getGroupId());
            updateById.setString(2, student.getFirstName());
            updateById.setString(3, student.getLastName());
            updateById.setInt(4, studentId);
        } catch (SQLException | IOException e) {
            System.err.println("Changes did not take effect");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int studentId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Students WHERE StudentId = ? ")) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void createTableCourses(Student student, Course course) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO StudentsCourses VALUES(?,?)")) {
            preparedStatement.setInt(1, student.getStudentId());
            preparedStatement.setInt(2, course.getCourseId());
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentFromCourse(Student student, Course course) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM StudentsCourses WHERE (StudentId = ?, CourseId = ?)")) {
            preparedStatement.setInt(1, student.getStudentId());
            preparedStatement.setInt(2, course.getCourseId());
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<Student> findStudentsByCourse(Course course) {
        List<Student> studentsList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT Students.StudentId,Students.FirstName,students.LastName,students.GroupId\n" +
                     "FROM students students\n" +
                     "INNER JOIN StudentsCourses StudentsCourses ON students.StudentId = StudentsCourses.StudentId\n" +
                     "INNER JOIN Courses Courses ON Courses.CourseId = StudentsCourses.CourseId\n" +
                     "WHERE Courses.CourseName LIKE ?")) {
            preparedStatement.setString(1, course.getCourseName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("StudentId"));
                student.setGroupId(resultSet.getInt("GroupId"));
                student.setFirstName(resultSet.getString("FirstName"));
                student.setLastName(resultSet.getString("LastName"));
                studentsList.add(student);
            }
            return studentsList;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return studentsList;
    }
}
