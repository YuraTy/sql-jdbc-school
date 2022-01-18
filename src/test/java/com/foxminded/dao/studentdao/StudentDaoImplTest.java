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
        statement.execute("DROP TABLE studentsCourses;");
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
        studentDao.create(testStudent1());
        studentDao.create(testStudent2());
        List<Student> actualList = studentDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void findId() {
        Student expectedStudent = testStudent1();
        studentDao.create(testStudent1());
        studentDao.create(testStudent2());
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
        studentDao.create(testStudent1());
        studentDao.create(testStudent2());
        studentDao.delete(1);
        List<Student> actualList = studentDao.findAll();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void findStudent() {
        studentDao.create(testStudent1());
        studentDao.create(testStudent2());
        courseDao.create(testCourse1());
        courseDao.create(testCourse2());
        studentDao.createTableCourses(testStudent1(), testCourse1());
        studentDao.createTableCourses(testStudent1(), testCourse2());
        studentDao.createTableCourses(testStudent2(), testCourse2());
        List<Student> expectedList = new ArrayList<>();
        expectedList.add(testStudent1());
        expectedList.add(testStudent2());
        List<Student> actualList = studentDao.findStudent("astronomy");
        Assertions.assertEquals(expectedList, actualList);
    }

    private Student testStudent1() {
        Student expectedStudent = new Student();
        expectedStudent.setStudentId(1);
        expectedStudent.setFirstName("Vitaly");
        expectedStudent.setLastName("Akimenko");
        return expectedStudent;
    }

    private Student testStudent2() {
        Student expectedStudent = new Student();
        expectedStudent.setStudentId(2);
        expectedStudent.setFirstName("Umar");
        expectedStudent.setLastName("Aleksandrenko");
        return expectedStudent;
    }

    private Course testCourse1() {
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseName("mathematics");
        course.setCourseDescription("multiplication and division");
        return course;
    }

    private Course testCourse2() {
        Course course = new Course();
        course.setCourseId(2);
        course.setCourseName("astronomy");
        course.setCourseDescription("space");
        return course;
    }
}