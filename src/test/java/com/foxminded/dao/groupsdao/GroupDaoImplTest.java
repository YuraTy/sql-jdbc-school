package com.foxminded.dao.groupsdao;

import com.foxminded.dao.studentdao.StudentDaoImpl;
import com.foxminded.datasource.DataSource;
import com.foxminded.groups.Group;
import com.foxminded.student.Student;
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

    private final DataSource dataSource = new DataSource();
    private final GroupDaoImpl groupDao  = new GroupDaoImpl();
    private final StudentDaoImpl studentDao = new StudentDaoImpl();

    @BeforeEach
    private void createTable() throws SQLException, IOException {
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableGroups.sql"));
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/createTableStudents.sql"));
    }

    @AfterEach
    private void delTable() throws SQLException, IOException {
        Statement statement = dataSource.getConnection().createStatement();
        statement.execute("DROP TABLE groups;");
        statement.execute("DROP TABLE students;");
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

    @Test
    void findGroups() {
    studentDao.create(testStudent1());
    studentDao.create(testStudent2());
    studentDao.create(testStudent3());
    groupDao.create(testGroup1());
    groupDao.create(testGroup2());
    List<Group> actualList = groupDao.findGroups(1);
    List<Group> expectedList = new ArrayList<>();
    expectedList.add(testGroup1());
    Assertions.assertEquals(expectedList,actualList);
    }

    private Group testGroup1 (){
        Group group = new Group();
        group.setGroupName("NG-22");
        return group;
    }

    private Group testGroup2 (){
        Group group = new Group();
        group.setGroupName("DC-63");
        return group;
    }

    private Student testStudent1() {
        Student student = new Student();
        student.setLastName("Ivan");
        student.setLastName("Pavlovich");
        student.setGroupId(1);
        return student;
    }

    private Student testStudent2() {
        Student student = new Student();
        student.setLastName("Nikolay");
        student.setLastName("Bobrov");
        student.setGroupId(2);
        return student;
    }

    private Student testStudent3() {
        Student student = new Student();
        student.setLastName("Vova");
        student.setLastName("Dudkin");
        student.setGroupId(2);
        return student;
    }
}