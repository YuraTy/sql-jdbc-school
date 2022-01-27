package com.foxminded.stringformatter;

import com.foxminded.randomdata.RandomData;
import com.foxminded.services.courseservices.CourseServices;
import com.foxminded.services.groupservices.GroupServices;
import com.foxminded.services.studentservices.StudentServices;
import com.foxminded.student.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class StringFormatterTest {

    @Mock
    StudentServices studentServices;

    @Mock
    CourseServices courseServices;

    @Mock
    GroupServices groupServices;

    @InjectMocks
    StringFormatter stringFormatter;

    @Test
    void formatterFindGroupsByNumberOfStudents() {
        stringFormatter.formatterFindGroupsByNumberOfStudents(10);
        Mockito.verify(groupServices).findGroupsByNumberOfStudents(Mockito.anyInt());
    }

    @Test
    void formatterStudentsByCourse() {
        Mockito.when(studentServices.findStudentsByCourse(Mockito.any())).thenReturn(testRandomStudent());
        stringFormatter.formatterStudentsByCourse("mathematics");
        Mockito.verify(studentServices).findStudentsByCourse(Mockito.any());
    }

    @Test
    void allListCourse() {
        courseServices.getAllCourse();
        Mockito.verify(courseServices).getAllCourse();
    }

    @Test
    void allListStudent() {
        studentServices.getAllStudent();
        Mockito.verify(studentServices).getAllStudent();
    }

    private List<Student> testRandomStudent() {
        String[] nameStudent = {"Vitaly", "Umar", "Renat", "Imran", "Artyom", "Salim", "Islam", "Yaroslav", "Ethan", "Peter", "Eva", "Eileen", "Tina", "Lera", "Anastasia", "Zoya", "Damira", "Yana", "Evelina", "Victoria"};
        String[] surnameStudent = {"Akimenko", "Aleksandrenko", "Alekseenko", "Andrievskiy", "Andriyashev", "Ardashev", "Artemenko", "Babarika", "Babich", "Vasilevskiy", "Vasilyuk", "Gavrilyuk", "Daragan", "Derevyanko", "Dzyuba", "Efimenko", "Zheleznyak", "Ischenko", "Kalenichenko", "Karpenko"};
        int randomNumber = (int) (Math.random() * 20);
        return Stream.generate(Student::new)
                .peek(p -> p.setLastName(surnameStudent[randomNumber]))
                .peek(p-> p.setFirstName(nameStudent[randomNumber]))
                .limit(200)
                .collect(Collectors.toList());
    }
}