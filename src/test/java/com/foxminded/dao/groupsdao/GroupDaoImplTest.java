package com.foxminded.dao.groupsdao;

import com.foxminded.datasource.DataSource;
import com.foxminded.groups.Group;
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

class GroupDaoImplTest {

    private DataSource dataSource = new DataSource();
    private GroupDaoImpl groupDao  = new GroupDaoImpl();

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
    void create() {
        Group expectedGroup = testGroup1();
        groupDao.create(testGroup1());
        Group actualGroup = groupDao.findId(1);
        Assertions.assertEquals(expectedGroup,actualGroup);
    }

    @Test
    void findAll() {
        List<Group> expectedList = new ArrayList<>();
        expectedList.add(testGroup1());
        expectedList.add(testGroup2());
        groupDao.create(testGroup1());
        groupDao.create(testGroup2());
        List<Group> actualList = groupDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void findId() {
        Group expectedGroup = testGroup2();
        groupDao.create(testGroup1());
        groupDao.create(testGroup2());
        Group actualGroup = groupDao.findId(2);
        Assertions.assertEquals(expectedGroup,actualGroup);
    }

    @Test
    void update() {
        Group expectedGroup = testGroup2();
        Group updateGroup = testGroup2();
        groupDao.create(testGroup1());
        groupDao.update(updateGroup,1);
        Group actualGroup = groupDao.findId(1);
        Assertions.assertEquals(expectedGroup,actualGroup);
    }

    @Test
    void delete() {
        List<Group> expectedList = new ArrayList<>();
        expectedList.add(testGroup1());
        groupDao.create(testGroup1());
        groupDao.create(testGroup2());
        groupDao.delete(2);
        List<Group> actualList = groupDao.findAll();
        Assertions.assertEquals(expectedList,actualList);
    }

    private Group testGroup1 (){
        Group group = new Group();
        group.setGroupId(1);
        group.setGroupName("NG-22");
        return group;
    }

    private Group testGroup2 (){
        Group group = new Group();
        group.setGroupId(2);
        group.setGroupName("DC-63");
        return group;
    }
}