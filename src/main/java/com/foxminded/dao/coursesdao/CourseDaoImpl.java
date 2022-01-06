package com.foxminded.dao.coursesdao;

import com.foxminded.course.Course;
import com.foxminded.datasource.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {

    private final DataSource dataSource = new DataSource();

    @Override
    public void create(Course course) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO courses SET courseName=? , courseDescription=?")) {
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCourseDescription());
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> findAll() {
        List<Course> allCourse = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT courseId , courseName , courseDescription FROM courses");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseId(resultSet.getInt("courseId"));
                course.setCourseName(resultSet.getString("courseName"));
                course.setCourseDescription(resultSet.getString("courseDescription"));
                allCourse.add(course);
            }
            return allCourse;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return allCourse;
    }

    @Override
    public Course findId(int courseId) {
        Course course = new Course();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT courseId , courseName , courseDescription FROM courses WHERE courseId=?")) {
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            course.setCourseId(resultSet.getInt("courseId"));
            course.setCourseName(resultSet.getString("courseName"));
            course.setCourseDescription(resultSet.getString("courseDescription"));
            return course;
        } catch (SQLException | IOException e) {
            System.err.println("Failed to read data from database");
            e.printStackTrace();
        }
        return course;
    }

    @Override
    public void update(Course course, int courseId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE courses SET courseName=? , courseDescription=? WHERE courseId=?")) {
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setString(2, course.getCourseDescription());
            preparedStatement.setInt(3, courseId);
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            System.err.println("Changes did not take effect");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int courseId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM courses WHERE courseId=?")) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
