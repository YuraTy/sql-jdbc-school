package com.foxminded.datasource;

import com.foxminded.connectionparameters.ConnectionParameters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    ConnectionParameters parameters = new ConnectionParameters();

    public Connection connecting () throws ClassNotFoundException {
        Connection connection = null;
        Class.forName("org.postgresql.Driver");
        try {
            connection = DriverManager.getConnection(parameters.getDB_URL(),parameters.getUSER(),parameters.getPASS());
            return connection;
        }catch (SQLException e){
            System.out.println("Connection Failed");
            e.printStackTrace();
            return connection;
        }
    }

}
