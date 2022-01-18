package com.foxminded.operationssql;

import com.foxminded.course.Course;
import com.foxminded.dao.coursesdao.CourseDaoImpl;
import com.foxminded.dao.studentdao.StudentDaoImpl;
import com.foxminded.datasource.DataSource;
import com.foxminded.groups.Group;
import com.foxminded.student.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OperationsSQL {

    private final DataSource dataSource = new DataSource();
    private final StudentDaoImpl studentDao = new StudentDaoImpl();






    public List<Student> findStudent (String namedCourse) {
        List<Course> courseList = new CourseDaoImpl().findAll();
        Optional<Course> optionalInteger = courseList.stream()
                .filter(p-> Objects.equals(p.getCourseName(), namedCourse))
                .findAny();
        int idCourse = 0;
        if (optionalInteger.isPresent()){
            idCourse = optionalInteger.get().getCourseId();
        }
        List<Student> studentsList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT groupId , firstName , lastName FROM students , studentsCourses WHERE studentsCourses.courseId = ?")) {
            preparedStatement.setInt(1, idCourse);
            ResultSet resultSet = preparedStatement.executeQuery();
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
}
