package com.foxminded.workdatabase;

import com.foxminded.dao.Dao;

import java.io.IOException;
import java.sql.SQLException;

public class WorkDatabase {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        Dao dao = new Dao();
        dao.connecting();
        dao.createDatabase();
        dao.createTableCourses();
        dao.createTableStudents();
        dao.createTableGroups();
        dao.visual();
    }
}
