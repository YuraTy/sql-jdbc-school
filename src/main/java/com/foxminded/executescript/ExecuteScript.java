package com.foxminded.executescript;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import org.apache.ibatis.jdbc.ScriptRunner;

public class ExecuteScript {

    public void runeScript(String patchScriptSQL, Connection dataSource) throws SQLException, IOException {
        try (dataSource) {
            ScriptRunner scriptRunner = new ScriptRunner(dataSource);
            try (FileReader file = new FileReader(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(patchScriptSQL)).getFile())) {
                scriptRunner.runScript(file);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }
}