package com.foxminded.dao.coursesdao;

import com.foxminded.course.Course;
import com.foxminded.datasource.DataSource;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class CourseDaoImplTest {

    private DataSource dataSource = new DataSource();
    private CourseDaoImpl courseDao = new CourseDaoImpl();

    @BeforeEach
    private void createTable() throws SQLException, IOException {
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableCourses.sql"));
    }

    @AfterEach
    private void delTable() throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE courses;");
    }

    @Test
    void create() {
        Course expectedCourse = new Course(1,"mathematics");
        courseDao.create(new Course(1,"mathematics"));
        Course actualCourse = courseDao.findId(1);
        Assertions.assertEquals(expectedCourse,actualCourse);
    }

    @Test
    void findAll() {
        List<Course> expectedList = new ArrayList<>() ;
        expectedList.add(new Course(1,"mathematics"));
        expectedList.add(new Course(2,"astronomy"));
        Stream.of(new Course(1,"mathematics"),new Course(2,"astronomy")).forEach(courseDao::create);
        List<Course> actualList = courseDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void findId() {
        Course expectedCourse = new Course(2,"astronomy");
        courseDao.create(new Course(1,"mathematics"));
        courseDao.create(new Course(2,"astronomy"));
        Course actualCourse = courseDao.findId(2);
        Assertions.assertEquals(expectedCourse,actualCourse);
    }

    @Test
    void update() {
        Course expectedCourse = new Course(2,"astronomy");
        Course updateCourse = new Course(2,"astronomy");
        courseDao.create(new Course(1,"mathematics"));
        courseDao.update(updateCourse,1);
        Course actualCourse = courseDao.findId(1);
        Assertions.assertEquals(expectedCourse,actualCourse);
    }

    @Test
    void delete() {
        List<Course> expectedList = new ArrayList<>() ;
        expectedList.add(new Course(1,"mathematics"));
        Stream.of(new Course(1,"mathematics"),new Course(2,"astronomy")).forEach(courseDao::create);
        courseDao.delete(2);
        List<Course> actualList = courseDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }
}