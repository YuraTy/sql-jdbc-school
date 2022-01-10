package com.foxminded.school;
;
import com.foxminded.groups.Group;
import com.foxminded.randomdata.RandomData;
import com.foxminded.student.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



public class School {
    public static void main(String[] args) throws IOException, SQLException {
        RandomData randomData = new RandomData();
        List<Student> list = randomData.randomStudent();
        for (Student st: list){
            System.out.println(st.getLastName());
        }

    }
}
