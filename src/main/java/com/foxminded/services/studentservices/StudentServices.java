package com.foxminded.services.studentservices;

import com.foxminded.dao.groupsdao.GroupDaoImpl;
import com.foxminded.dao.studentdao.StudentDaoImpl;
import com.foxminded.groups.Group;
import com.foxminded.randomdata.RandomData;
import com.foxminded.student.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentServices {

    private final StudentDaoImpl studentDao = new StudentDaoImpl();
    private final GroupDaoImpl groupDao = new GroupDaoImpl();
    private final RandomData randomData = new RandomData();
    private static final Map<Group, Integer> sizeCount = new HashMap<>();
    private final List<Group> groupList = groupDao.findAll();
    private final int randomNumber = randomData.randomNumber(0, 19);

    public void fillingStudentsDB() {
        List<Student> studentList = randomData.randomStudent();
        studentList.stream()
                .peek(p -> p.setGroupId(checkGroup(groupList.get(randomNumber)).getGroupId()))
                .forEach(studentDao::create);
    }

    private Group checkGroup(Group group) {
        if (sizeCount.get(group) >= 10 && sizeCount.get(group) <= 30) {
            sizeCount.put(group, sizeCount.get(group) + 1);
            return group;
        } else return groupList.get(randomNumber);
    }
}
