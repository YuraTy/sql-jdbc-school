package com.foxminded.datasource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    private static Connection connection = null;
    private static FileInputStream fileInputStream = null;

    public Connection getConnection () throws ClassNotFoundException, IOException {
        Class.forName("org.postgresql.Driver");
        try {
            fileInputStream = new FileInputStream("src/main/resources/config.properties");
            Properties properties = new Properties();
            properties.load(fileInputStream);
            String host = properties.getProperty("db.host");
            String login = properties.getProperty("db.login");
            String pass = properties.getProperty("db.password");
            connection = DriverManager.getConnection(host,login,pass);
            return connection;
        }catch (SQLException | FileNotFoundException e){
            System.out.println("Connection Failed");
            e.printStackTrace();
            return connection;
        }finally {
            assert fileInputStream != null;
            fileInputStream.close();
        }
    }

}
