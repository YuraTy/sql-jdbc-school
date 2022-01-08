package com.foxminded.school;
;
import com.foxminded.groups.Group;
import com.foxminded.randomdata.RandomData;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



public class School {
    public static void main(String[] args) throws IOException, SQLException {
        RandomData randomData = new RandomData();
        List<Group> list = randomData.randomGroups();
        for (Group st: list){
            System.out.println(st.getGroupName());
        }

    }
}
