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
    }

    @AfterEach
    private void delTable() throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE students;");
        statement.execute("DROP TABLE groups;");
    }

    @Test
    void fillingStudentsDB() {
        groupServices.fillingGroupsDB();
        studentServices.fillingStudentsDB();
        Mockito.verify(studentDao,Mockito.times(200)).create(Mockito.any());
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
        studentServices.addStudentForCourse(testStudent1(),testCourse1());
        Mockito.verify(studentDao).createTableCourses(Mockito.any(),Mockito.any());
    }

    @Test
    void deleteCourseForStudent() {
        studentServices.deleteCourseForStudent(testStudent1(),testCourse1());
        Mockito.verify(studentDao).deleteStudentFromCourse(Mockito.any(),Mockito.any());
    }

    private Student testStudent1 () {
        Student expectedStudent = new Student();
        expectedStudent.setStudentId(1);
        expectedStudent.setFirstName("Vitaly");
        expectedStudent.setLastName("Akimenko");
        return expectedStudent;
    }

    private Course testCourse1 (){
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseName("mathematics");
        course.setCourseDescription("multiplication and division");
        return course;
    }
}