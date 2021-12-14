package com.foxminded.dao;

import java.io.IOException;
import java.sql.*;

public class Dao {

    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/my_bd";
    private static final String USER = "postgres";
    private static final String PASS = "qwer";
    private static Statement statement = null;

    public Connection connecting () throws ClassNotFoundException {
        Connection connection = null;
        Class.forName("org.postgresql.Driver");

        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
            return connection;
        }catch (SQLException e){
            System.out.println("Connection Failed");
            e.printStackTrace();
            return connection;
        }
    }

    public void createTableGroups () throws ClassNotFoundException, SQLException {
        String sql = "CREATE TABLE groups " +
                "group_id int," +
                "group_name String)";
        statement = connecting().createStatement();
        statement.execute(sql);
        System.out.println("Create table");
    }

    public void createTableStudents () throws ClassNotFoundException, SQLException {
        String sql = "CREATE TABLE students " +
                "student_id int," +
                "group_id int," +
                "first_name String," +
                "last_name String)";
        statement = connecting().createStatement();
        statement.execute(sql);
        System.out.println("Create table");
    }

    public void createTableCourses () throws ClassNotFoundException, SQLException {
        String sql = "CREATE TABLE courses " +
                "course_id INT," +
                "course_name String," +
                "course_description String)";
        statement = connecting().createStatement();
        statement.execute(sql);
        System.out.println("Create table");
    }

    public void visual () throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM pg_catalog.pg_tables";
        statement = connecting().createStatement();
        System.out.println(statement.execute(sql));
    }

    public void createDatabase () throws IOException {
        String sql = "CREATE DATABASE university";
        try(Connection bd = DriverManager.getConnection(DB_URL,USER,PASS)) {
            Statement statement = bd.createStatement();
            statement.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
