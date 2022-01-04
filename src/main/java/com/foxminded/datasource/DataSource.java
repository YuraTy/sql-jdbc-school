package com.foxminded.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    
    private String properties = "config.properties";

    private static final String driverSQL = "org.postgresql.Driver";


    static {
        try {
            Class.forName(driverSQL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DataSource(String properties) {
        this.properties = properties;
    }
    
    public DataSource(){}

    public Connection getConnection () throws IOException, SQLException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(properties)){
            Properties properties = new Properties();
            properties.load(inputStream);
            String host = properties.getProperty("db.host");
            String login = properties.getProperty("db.login");
            String pass = properties.getProperty("db.password");
            return DriverManager.getConnection(host,login,pass);
        }
    }

}
