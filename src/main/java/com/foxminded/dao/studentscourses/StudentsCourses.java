package com.foxminded.dao.studentscourses;

import com.foxminded.course.Course;
import com.foxminded.datasource.DataSource;
import com.foxminded.student.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentsCourses {

    private DataSource dataSource = new DataSource();

    public void create(Student student, Course course) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO studentsCourses VALUES(?,?)")) {
            preparedStatement.setInt(1, student.getStudentId());
            preparedStatement.setInt(2, course.getCourseId());
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
