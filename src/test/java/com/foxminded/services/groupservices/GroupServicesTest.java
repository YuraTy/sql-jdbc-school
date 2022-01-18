package com.foxminded.services.groupservices;

import com.foxminded.dao.groupsdao.GroupDaoImpl;
import com.foxminded.datasource.DataSource;
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
class GroupServicesTest {

    private final DataSource dataSource = new DataSource();

    @Mock
    private GroupDaoImpl groupDao;

    @InjectMocks
    private GroupServices groupServices;

    @BeforeEach
    private void createTable() throws SQLException, IOException {
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableGroups.sql"));
    }

    @AfterEach
    private void delTable() throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE groups;");
    }

    @Test
    void fillingGroupsDB() {
        groupServices.fillingGroupsDB();
        Mockito.verify(groupDao, Mockito.times(20)).create(Mockito.any());
    }

    @Test
    void findGroups() {
        groupServices.findGroups(2);
        Mockito.verify(groupDao).findGroups(Mockito.anyInt());
    }
}