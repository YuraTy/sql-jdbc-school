package com.foxminded.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    static {
        try(InputStream inputStream = DataSource.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            Class.forName(properties.getProperty("db.driver"));
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection () throws IOException, SQLException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);
            String host = properties.getProperty("db.host");
            String login = properties.getProperty("db.login");
            String pass = properties.getProperty("db.password");
            return DriverManager.getConnection(host,login,pass);
        }
    }

}
