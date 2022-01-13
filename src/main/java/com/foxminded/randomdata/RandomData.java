package com.foxminded.randomdata;

import com.foxminded.course.Course;
import com.foxminded.groups.Group;
import com.foxminded.student.Student;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomData {

    public List<Course> randomCourses () {
        List<String> coursesLis = Arrays.asList("mathematics", "rhetoric", "drawing", "drafting", "astronomy", "ecology", "philosophy", "jurisprudence", "economics", "natural science", "chemistry", "physics", "geometry", "algebra", "informatics", "biology", "history", "geography", "technology", "foreign language");
        List<Course> list = new ArrayList<>();
        for (String s:coursesLis){
            Course course = new Course();
            course.setCourseName(s);
            list.add(course);
        }
        return list;
    }

    private String randomGroup() {
        return RandomStringUtils.randomAlphabetic(2) + "-" + randomNumber(10, 99);
    }

    public List<Group> randomGroups() {
        return Stream.generate(Group::new)
                .peek(p -> p.setGroupName(randomGroup()))
                .limit(20)
                .collect(Collectors.toList());
    }

    public List<Student> randomStudent() {
        String[] nameStudent = {"Vitaly", "Umar", "Renat", "Imran", "Artyom", "Salim", "Islam", "Yaroslav", "Ethan", "Peter", "Eva", "Eileen", "Tina", "Lera", "Anastasia", "Zoya", "Damira", "Yana", "Evelina", "Victoria"};
        String[] surnameStudent = {"Akimenko", "Aleksandrenko", "Alekseenko", "Andrievskiy", "Andriyashev", "Ardashev", "Artemenko", "Babarika", "Babich", "Vasilevskiy", "Vasilyuk", "Gavrilyuk", "Daragan", "Derevyanko", "Dzyuba", "Efimenko", "Zheleznyak", "Ischenko", "Kalenichenko", "Karpenko"};
        return Stream.generate(Student::new)
                .peek(p -> p.setLastName(surnameStudent[randomNumber(0,20)]))
                .peek(p-> p.setFirstName(nameStudent[randomNumber(0, 20)]))
                .limit(200)
                .collect(Collectors.toList());
    }

    public int randomNumber(int start, int finale) {
        return start + (int) (Math.random() * finale);
    }

}
