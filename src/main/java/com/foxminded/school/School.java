package com.foxminded.school;

import com.foxminded.datasource.DataSource;
import com.foxminded.randomdata.RandomData;

import java.io.IOException;


public class School {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DataSource dataSource = new DataSource();
        dataSource.getConnection();
        RandomData randomData = new RandomData();
        System.out.println(randomData.random());

    }
}