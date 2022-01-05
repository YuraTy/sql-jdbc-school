package com.foxminded.school;

import com.foxminded.dao.studentdao.StudentDaoImpl;
import com.foxminded.datasource.DataSource;
import com.foxminded.randomdata.RandomData;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;


public class School {
    public static void main(String[] args) throws IOException, SQLException {
        DataSource dataSource = new DataSource();
        StudentDaoImpl studentDao = new StudentDaoImpl();
        RunScript.execute(dataSource.getConnection(), new FileReader("src/main/resources/createTableStudents.sql"));
        RandomData randomData = new RandomData();
        List<String> list = randomData.randomStudent();
        for (String st: list){
            System.out.println(st);
        }

    }
}
