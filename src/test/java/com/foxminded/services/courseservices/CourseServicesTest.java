package com.foxminded.services.courseservices;

import com.foxminded.dao.coursesdao.CourseDaoImpl;
import com.foxminded.dao.studentdao.StudentDaoImpl;
import com.foxminded.datasource.DataSource;
import com.foxminded.services.groupservices.GroupServices;
import com.foxminded.services.studentservices.StudentServices;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CourseServicesTest {

    private final DataSource dataSource = new DataSource();
    private final GroupServices groupServices = new GroupServices();
    private final StudentServices studentServices = new StudentServices();

    @Mock
    private CourseDaoImpl courseDao;

    @Mock
    private StudentDaoImpl studentDao;

    @InjectMocks
    private CourseServices courseServices;


    @BeforeEach
    private void createTable() throws SQLException, IOException {
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableGroups.sql"));
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableStudents.sql"));
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableCourses.sql"));
    }

    @AfterEach
    private void delTable() throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE courses;");
        statement.execute("DROP TABLE students;");
        statement.execute("DROP TABLE groups;");
    }


    @Test
    void fillingCoursesDB(){
        courseServices.fillingCoursesDB();
        Mockito.verify(courseDao,Mockito.times(20)).create(Mockito.any());
    }

    @Test
    void fillingStudentsCourses() {
        groupServices.fillingGroupsDB();
        studentServices.fillingStudentsDB();
        courseServices.fillingCoursesDB();
        courseServices.fillingStudentsCourses();
        Mockito.verify(studentDao,Mockito.times(200)).createTableCourses(Mockito.any(),Mockito.any());

    }

}