package com.foxminded.services.studentservices;

import com.foxminded.dao.studentdao.StudentDaoImpl;
import com.foxminded.datasource.DataSource;
import com.foxminded.services.groupservices.GroupServices;
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
}