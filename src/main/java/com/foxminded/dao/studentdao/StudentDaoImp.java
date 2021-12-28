package com.foxminded.dao.studentdao;

import com.foxminded.datasource.DataSource;
import com.foxminded.student.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImp implements StudentDao {

    private final DataSource dataSource = new DataSource();
    private final Connection connection = dataSource.getConnection();

    public StudentDaoImp() throws SQLException, IOException {
    }


    @Override
    public void create(String firstName, String lastName, int groupId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students VALUES (" + groupId + ", " + firstName + " , " + lastName + ")")) {
            preparedStatement.execute();
            System.out.println("Data recorded");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> studentsList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT studentId , groupId , firstName , lastName FROM students");
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("studentId"));
                student.setGroupId(resultSet.getInt("groupId"));
                student.setFirstName(resultSet.getString("firstName"));
                student.setFirstName(resultSet.getString("lastName"));
                studentsList.add(student);
            }
            return studentsList;
        } catch (SQLException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return studentsList;
    }

    @Override
    public Student findId(int studentId) {
        Student student = new Student();
        try (PreparedStatement findById = connection.prepareStatement("SELECT groupId , firstName , lastName FROM students WHERE studentId = " + studentId);
             ResultSet resultSet = findById.executeQuery()){
            student.setStudentId(resultSet.getInt("studentId"));
            student.setGroupId(resultSet.getInt("groupId"));
            student.setFirstName(resultSet.getString("firstName"));
            student.setFirstName(resultSet.getString("lastName"));
            return student;
        } catch (SQLException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void update(int groupId, String firstName, String lastName, int studentId) {
        try (PreparedStatement updateById = connection.prepareStatement("UPDATE students SET groupId = ? , firstName = ? , lastName = ? WHERE studentId = ?")){
            updateById.setInt(1, groupId);
            updateById.setString(2, firstName);
            updateById.setString(3, lastName);
            updateById.setInt(4, studentId);
        } catch (SQLException e) {
            System.err.println("Changes did not take effect");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int studentId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students WHERE studentId = " + studentId)){
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
