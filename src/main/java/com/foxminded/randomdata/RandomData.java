package com.foxminded.randomdata;

import com.foxminded.course.Course;
import com.foxminded.groups.Group;
import com.foxminded.student.Student;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class RandomData {

    public List<Course> randomCourses () {
        List<String> coursesLis = Arrays.asList("mathematics", "rhetoric", "drawing", "drafting", "astronomy", "ecology", "philosophy", "jurisprudence", "economics", "natural science", "chemistry", "physics", "geometry", "algebra", "informatics", "biology", "history", "geography", "technology", "foreign language");
        return coursesLis.stream().map(Course::new).collect(Collectors.toList());
    }

    private String randomGroup() {
        return RandomStringUtils.randomAlphabetic(2) + "-" + randomNumber(10, 99);
    }

    public List<Group> randomGroups() {
        List<Group> groupList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Group group = new Group();
            group.setGroupName(randomGroup());
            groupList.add(group);
        }
        return groupList;
    }

    public List<Student> randomStudent() {
        String[] nameStudent = {"Vitaly", "Umar", "Renat", "Imran", "Artyom", "Salim", "Islam", "Yaroslav", "Ethan", "Peter", "Eva", "Eileen", "Tina", "Lera", "Anastasia", "Zoya", "Damira", "Yana", "Evelina", "Victoria"};
        String[] surnameStudent = {"Akimenko", "Aleksandrenko", "Alekseenko", "Andrievskiy", "Andriyashev", "Ardashev", "Artemenko", "Babarika", "Babich", "Vasilevskiy", "Vasilyuk", "Gavrilyuk", "Daragan", "Derevyanko", "Dzyuba", "Efimenko", "Zheleznyak", "Ischenko", "Kalenichenko", "Karpenko"};
        List<Student> studentsList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Student student = new Student();
            student.setLastName(surnameStudent[randomNumber(0, 20)]);
            student.setFirstName(nameStudent[randomNumber(0, 20)]);
            studentsList.add(student);
        }
        return studentsList;
    }

    public int randomNumber(int start, int finale) {
        return start + (int) (Math.random() * finale);
    }

}
