package com.foxminded.connectionparameters;

import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;

public class ConnectionParameters {

    private final Map <String ,String> mapParameters = loginParameters();

    public String getDB_URL() {
        return DB_URL;
    }

    public String getPASS() {
        return PASS;
    }

    public String getUSER() {
        return USER;
    }

    private final String DB_URL = mapParameters.get("DB_URL");
    private final String PASS = mapParameters.get("PASS");
    private final String USER = mapParameters.get("USER");

    private Map<String, String> loginParameters() {
        InputStream propertiesStream = ClassLoader.getSystemClassLoader().getResourceAsStream("parameters");
        assert propertiesStream != null;
        return new BufferedReader(new InputStreamReader(propertiesStream)).lines()
                .collect(Collectors.toMap( k -> (k.toString().split(" "))[0] ,v-> (v.toString().split(" "))[1]));
    }

}
