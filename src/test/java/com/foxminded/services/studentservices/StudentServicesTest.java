package com.foxminded.services.studentservices;

import com.foxminded.course.Course;
import com.foxminded.dao.studentdao.StudentDaoImpl;
import com.foxminded.datasource.DataSource;
import com.foxminded.services.groupservices.GroupServices;
import com.foxminded.student.Student;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

@ExtendWith(MockitoExtension.class)
class StudentServicesTest {

    private final DataSource dataSource = new DataSource();
    private final GroupServices groupServices = new GroupServices();

    @Mock
    private StudentDaoImpl studentDao;

    @InjectMocks
    private StudentServices studentServices;


    @BeforeEach
    private void createTable() throws SQLException, IOException {
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableGroups.sql"));
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableStudents.sql"));
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableCourses.sql"));
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableStudentsCourses.sql"));
    }

    @AfterEach
    private void delTable() throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE students_courses;");
        statement.execute("DROP TABLE students;");
        statement.execute("DROP TABLE groups;");
        statement.execute("DROP TABLE courses;");
    }

    @Test
    void fillingStudentsDB() {
        groupServices.fillingGroupsDB();
        studentServices.fillingStudentsDB();
        Mockito.verify(studentDao, Mockito.times(200)).create(Mockito.any());
    }

    @Test
    void newStudent() {
        studentServices.newStudent(testStudent1());
        Mockito.verify(studentDao).create(Mockito.any());
    }

    @Test
    void deletedStudent() {
        studentServices.newStudent(testStudent1());
        studentServices.deleteStudent(1);
        Mockito.verify(studentDao).delete(1);
    }

    @Test
    void addStudentOnCourse() {
        studentServices.addStudentForCourse(testStudent1(), testCourse1());
        Mockito.verify(studentDao).addStudentToCourse(Mockito.any(), Mockito.any());
    }

    @Test
    void deleteCourseForStudent() {
        studentServices.deleteCourseForStudent(testStudent1(), testCourse1());
        Mockito.verify(studentDao).deleteStudentFromCourse(Mockito.any(), Mockito.any());
    }

    @Test
    void findStudentsByCourse() {
        studentServices.findStudentsByCourse(new Course(1,"mathematics"));
        Mockito.verify(studentDao).findStudentsByCourse(Mockito.any());
    }

    private Student testStudent1() {
        return new Student(1,"Vitaly","Akimenko");
    }

    private Course testCourse1() {
        return new Course(1,"mathematics");
    }
}