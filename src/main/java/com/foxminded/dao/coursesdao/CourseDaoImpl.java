package com.foxminded.dao.coursesdao;

import com.foxminded.course.Course;
import com.foxminded.datasource.DataSource;
import com.foxminded.executescript.ExecuteScript;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {

    private final DataSource dataSource = new DataSource();

    @Override
    public void create(Course course) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO courses (course_name , course_description) VALUES (?,?)")) {
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
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT course_id , course_name , course_description FROM courses");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseId(resultSet.getInt("course_id"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setCourseDescription(resultSet.getString("course_description"));
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
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT course_id , course_name , course_description FROM courses WHERE course_id=?")) {
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                course.setCourseId(resultSet.getInt("course_id"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setCourseDescription(resultSet.getString("course_description"));
            }
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
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE courses SET course_name=? , course_description=? WHERE course_id=?")) {
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
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM courses WHERE course_id=?")) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void createTableCourses() throws SQLException, IOException {
        new ExecuteScript().runScript("createTableCourses.sql",dataSource.getConnection());
    }

    public void deleteTableCourses() throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE courses;");
    }
}
