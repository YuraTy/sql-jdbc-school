package com.foxminded.dao.studentdao;

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


public class StudentDaoImplTest {

    Statement statement = null;

    private DataSource dataSource = new DataSource("config.properties");
    private StudentDaoImpl studentDao = new StudentDaoImpl();

    @BeforeEach
    private void createTable() throws SQLException, IOException {
        RunScript.execute(dataSource.getConnection(), new FileReader("E:/sql-jdbc-school/src/test/resources/createTableStudents.sql"));
    }

    @AfterEach
    private void delTable() throws SQLException, IOException {
    statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE students;");
    }

    @Test
    void create() throws Exception {
        Student expectedStudent = testStudent1();
        studentDao.create(1,"Vitaly","Akimenko",2);
        Student actualStudent = studentDao.findAll().get(0);
        Assertions.assertEquals(expectedStudent,actualStudent);
    }

    @Test
    void findAll() {
        List<Student> expectedList = new ArrayList<>() ;
        expectedList.add(testStudent1());
        expectedList.add(testStudent2());
        studentDao.create(1,"Vitaly","Akimenko",2);
        studentDao.create(2,"Umar","Aleksandrenko",45);
        List<Student> actualList = studentDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void findId() {
        Student expectedStudent = testStudent1();
        studentDao.create(1,"Vitaly","Akimenko",2);
        studentDao.create(2,"Umar","Aleksandrenko",45);
        Student actualStudent = studentDao.findId(1);
        Assertions.assertEquals(expectedStudent,actualStudent);
    }

    @Test
    void update() {
        Student expectedStudent = testStudent1();
        studentDao.create(1,"Vitaly","Akimenko",30);
        studentDao.update(2,"Vitaly","Akimenko",1);
        Student actualStudent = studentDao.findAll().get(0);
        Assertions.assertEquals(expectedStudent,actualStudent);
    }

    @Test
    void delete() {
        List<Student> expectedList = new ArrayList<>() ;
        expectedList.add(testStudent2());
        studentDao.create(1,"Vitaly","Akimenko",2);
        studentDao.create(2,"Umar","Aleksandrenko",45);
        studentDao.delete(1);
        List<Student> actualList = studentDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    private Student testStudent1 () {
        Student expectedStudent = new Student();
        expectedStudent.setStudentId(1);
        expectedStudent.setFirstName("Vitaly");
        expectedStudent.setLastName("Akimenko");
        expectedStudent.setGroupId(2);
        return expectedStudent;
    }
    private Student testStudent2 () {
        Student expectedStudent = new Student();
        expectedStudent.setStudentId(2);
        expectedStudent.setFirstName("Umar");
        expectedStudent.setLastName("Aleksandrenko");
        expectedStudent.setGroupId(45);
        return expectedStudent;
    }
}