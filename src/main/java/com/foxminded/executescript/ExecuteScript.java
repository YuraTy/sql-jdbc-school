package com.foxminded.executescript;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class ExecuteScript {

    public void runScript(String patchScriptSQL, Connection dataSource) throws SQLException{

        InputStream propertiesStream = ClassLoader.getSystemClassLoader().getResourceAsStream(patchScriptSQL);
        assert propertiesStream != null;
        String sqlScript = new BufferedReader(new InputStreamReader(propertiesStream)).lines()
                .collect(Collectors.joining("\n"));

        try (PreparedStatement preparedStatement = dataSource.prepareStatement(sqlScript)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}