package com.foxminded.stringformatter;

import com.foxminded.services.courseservices.CourseServices;
import com.foxminded.services.groupservices.GroupServices;
import com.foxminded.services.studentservices.StudentServices;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StringFormatterTest {

    @Mock
    CourseServices courseServices;

    @Mock
    GroupServices groupServices;

    @Mock
    StudentServices studentServices;

    @InjectMocks
    StringFormatter stringFormatter;

    @Test
    void formatterFindGroupsByNumberOfStudents() {
        stringFormatter.formatterFindGroupsByNumberOfStudents(10);
        Mockito.verify(groupServices).findGroupsByNumberOfStudents(Mockito.anyInt());
    }

    @Test
    void formatterStudentsByCourse() {
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
}