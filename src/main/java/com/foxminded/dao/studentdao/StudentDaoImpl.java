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

public class StudentDaoImpl implements StudentDao {

    private final DataSource dataSource = new DataSource("config.properties");


    @Override
    public void create(int studentId ,String firstName, String lastName, int groupId) {
        try (Connection connection = dataSource.getConnection();PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students SET firstName=? , lastName=? , groupId=? ,studentId=? ")) {
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setInt(3,groupId);
            preparedStatement.setInt(4,studentId);
            preparedStatement.execute();
            System.out.println("Data recorded");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> studentsList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();PreparedStatement preparedStatement = connection.prepareStatement("SELECT studentId , groupId , firstName , lastName FROM students");
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("studentId"));
                student.setGroupId(resultSet.getInt("groupId"));
                student.setFirstName(resultSet.getString("firstName"));
                student.setLastName(resultSet.getString("lastName"));
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
        try (Connection connection = dataSource.getConnection();PreparedStatement findById = connection.prepareStatement("SELECT groupId , firstName , lastName FROM students WHERE studentId=?")){
            findById.setInt(1,studentId); // for some reason gives an error "org.h2.jdbc.JdbcSQLSyntaxErrorException: Столбец "studentId" не найден"
            ResultSet resultSet = findById.executeQuery();
            while (resultSet.next()) {
                student.setStudentId(resultSet.getInt("studentId"));
                student.setGroupId(resultSet.getInt("groupId"));
                student.setFirstName(resultSet.getString("firstName"));
                student.setFirstName(resultSet.getString("lastName"));
            }
            return student;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void update(int groupId, String firstName, String lastName, int studentId) {
        try (Connection connection = dataSource.getConnection();PreparedStatement updateById = connection.prepareStatement("UPDATE students SET groupId = ? , firstName = ? , lastName = ? WHERE studentId = ?")){
            updateById.setInt(1, groupId);
            updateById.setString(2, firstName);
            updateById.setString(3, lastName);
            updateById.setInt(4, studentId);
        } catch (SQLException | IOException e) {
            System.err.println("Changes did not take effect");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int studentId) {
        try (Connection connection = dataSource.getConnection();PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students WHERE studentId =? " )){
            preparedStatement.setInt(1,studentId);
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
