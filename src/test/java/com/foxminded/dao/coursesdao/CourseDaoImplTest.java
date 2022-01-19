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
        Course expectedCourse = testCourse1();
        courseDao.create(testCourse1());
        Course actualCourse = courseDao.findId(1);
        Assertions.assertEquals(expectedCourse,actualCourse);
    }

    @Test
    void findAll() {
        List<Course> expectedList = new ArrayList<>() ;
        expectedList.add(testCourse1());
        expectedList.add(testCourse2());
        Stream.of(testCourse1(),testCourse1()).forEach(courseDao::create);
        List<Course> actualList = courseDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void findId() {
        Course expectedCourse = testCourse2();
        courseDao.create(testCourse1());
        courseDao.create(testCourse2());
        Course actualCourse = courseDao.findId(2);
        Assertions.assertEquals(expectedCourse,actualCourse);
    }

    @Test
    void update() {
        Course expectedCourse = testCourse2();
        Course updateCourse = testCourse2();
        courseDao.create(testCourse1());
        courseDao.update(updateCourse,1);
        Course actualCourse = courseDao.findId(1);
        Assertions.assertEquals(expectedCourse,actualCourse);
    }

    @Test
    void delete() {
        List<Course> expectedList = new ArrayList<>() ;
        expectedList.add(testCourse1());
        Stream.of(testCourse1(),testCourse1()).forEach(courseDao::create);
        courseDao.delete(2);
        List<Course> actualList = courseDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    private Course testCourse1 (){
        return new Course(1,"mathematics");
    }

    private Course testCourse2 (){
        return new Course(2,"astronomy");
    }
}