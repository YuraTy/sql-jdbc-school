package com.foxminded.datasource;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DataSourceTest {
    private static final String driverSQL = "org.h2.Driver";

    static {
        try {
            Class.forName(driverSQL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    String URL = "jdbc:h2:~/test";
    String login = "sa";
    String pas = "";

    @Test
    void getConnection() throws SQLException {
        try(Connection conn = DriverManager.getConnection(URL,login,pas)){
            Statement statement = conn.createStatement();
        }

    }
}