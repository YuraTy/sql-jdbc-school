package com.foxminded.dao.studentdao;

import com.foxminded.course.Course;
import com.foxminded.dao.coursesdao.CourseDaoImpl;
import com.foxminded.datasource.DataSource;
import com.foxminded.student.Student;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class StudentDaoImplTest {

    private final DataSource dataSource = new DataSource();
    private final StudentDaoImpl studentDao = new StudentDaoImpl();
    private final CourseDaoImpl courseDao = new CourseDaoImpl();

    @BeforeEach
    private void createTable() throws SQLException, IOException {
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableStudents.sql"));
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableCourses.sql"));
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableStudentsCourses.sql"));
    }

    @AfterEach
    private void delTable() throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE students_courses;");
        statement.execute("DROP TABLE students;");
        statement.execute("DROP TABLE courses;");
    }

    @Test
    void create() throws Exception {
        Student expectedStudent = testStudent1();
        studentDao.create(expectedStudent);
        Student actualStudent = studentDao.findId(1);
        Assertions.assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void findAll() {
        List<Student> expectedList = new ArrayList<>();
        expectedList.add(testStudent1());
        expectedList.add(testStudent2());
        Stream.of(testStudent1(),testStudent2()).forEach(studentDao::create);
        List<Student> actualList = studentDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void findId() {
        Student expectedStudent = testStudent1();
        Stream.of(testStudent1(),testStudent2()).forEach(studentDao::create);
        Student actualStudent = studentDao.findId(1);
        Assertions.assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void update() {
        Student expectedStudent = testStudent1();
        Student updateStudent = testStudent2();
        studentDao.create(testStudent1());
        studentDao.update(updateStudent, 1);
        Student actualStudent = studentDao.findAll().get(0);
        Assertions.assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void delete() {
        List<Student> expectedList = new ArrayList<>();
        expectedList.add(testStudent2());
        Stream.of(testStudent1(),testStudent2()).forEach(studentDao::create);
        studentDao.delete(1);
        List<Student> actualList = studentDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void findStudentsByCourse() {
        Stream.of(testStudent1(),testStudent2()).forEach(studentDao::create);
        Stream.of(testCourse1(),testCourse2()).forEach(courseDao::create);
        studentDao.addStudentToCourse(testStudent1(), testCourse1());
        studentDao.addStudentToCourse(testStudent1(), testCourse2());
        studentDao.addStudentToCourse(testStudent2(), testCourse2());
        List<Student> expectedList = new ArrayList<>();
        expectedList.add(testStudent1());
        expectedList.add(testStudent2());
        List<Student> actualList = studentDao.findStudentsByCourse(new Course(1,"astronomy"));
        Assertions.assertEquals(expectedList, actualList);
    }

    private Student testStudent1() {
        return new Student(1,"Vitaly","Akimenko");
    }

    private Student testStudent2() {
        return new Student(2,"Umar","Aleksandrenko");
    }

    private Course testCourse1() {
        return new Course(1,"mathematics");
    }

    private Course testCourse2() {
        return new Course(2,"astronomy");
    }
}