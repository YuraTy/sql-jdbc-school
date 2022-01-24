package com.foxminded.dao.studentdao;

import com.foxminded.course.Course;
import com.foxminded.datasource.DataSource;
import com.foxminded.executescript.ExecuteScript;
import com.foxminded.student.Student;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private final DataSource dataSource = new DataSource();

    @Override
    public void create(Student student) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students(first_name , last_name , group_id) VALUES (?,?,?)")) {
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
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT student_id , group_id , first_name , last_name FROM students");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setGroupId(resultSet.getInt("group_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
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
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT student_id , group_id , first_name , last_name FROM students WHERE student_id=?")) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student.setStudentId(resultSet.getInt("student_id"));
                student.setGroupId(resultSet.getInt("group_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
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
             PreparedStatement updateById = connection.prepareStatement("UPDATE students SET group_id = ? , first_name = ? , last_name = ? WHERE student_id = ?")) {
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
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students WHERE student_id = ? ")) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addStudentToCourse(Student student, Course course) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students_courses VALUES(?,?)")) {
            preparedStatement.setInt(1, student.getStudentId());
            preparedStatement.setInt(2, course.getCourseId());
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentFromCourse(Student student, Course course) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students_courses WHERE (student_id = ?, course_id = ?)")) {
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
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT students.student_id,students.first_name,students.last_name,students.group_id\n" +
                     "FROM students students\n" +
                     "INNER JOIN students_courses students_courses ON students.student_id = students_courses.student_id\n" +
                     "INNER JOIN courses courses ON courses.course_id = students_courses.course_id\n" +
                     "WHERE courses.course_name LIKE ?")) {
            preparedStatement.setString(1, course.getCourseName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setGroupId(resultSet.getInt("group_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                studentsList.add(student);
            }
            return studentsList;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return studentsList;
    }

    public void createTableStudents() throws SQLException, IOException {
        new ExecuteScript().runeScript("createTableStudents.sql",dataSource.getConnection());
    }

    public void deleteTableStudents() throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE students;");
    }

    public void createTableCourseStudent() throws SQLException, IOException {
        new ExecuteScript().runeScript("createTableStudentsCourses.sql",dataSource.getConnection());
    }

    public void deleteTableCourseStudent() throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE students_courses;");
    }
}
