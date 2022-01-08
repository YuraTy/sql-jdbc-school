package com.foxminded.services.courseservices;

import com.foxminded.course.Course;
import com.foxminded.dao.coursesdao.CourseDaoImpl;
import com.foxminded.dao.studentdao.StudentDaoImpl;
import com.foxminded.dao.studentscourses.StudentsCourses;
import com.foxminded.randomdata.RandomData;
import com.foxminded.student.Student;

import java.util.*;

public class CourseServices {

    private final StudentsCourses studentsCourses = new StudentsCourses();
    private final CourseDaoImpl courseDao = new CourseDaoImpl();
    private final RandomData randomData = new RandomData();
    private final StudentDaoImpl studentDao = new StudentDaoImpl();
    private static final Map<Student, Integer> courseSize = new HashMap<>();
    private final List<Student> studentList = studentDao.findAll();
    private final int randomNumber = randomData.randomNumber(1, 3);

    public void fillingCoursesDB() {
        randomData.randomCourses().forEach(courseDao::create);
    }

    public void fillingStudentsCourses() {
        List<Course> courseList = courseDao.findAll();
        studentList.forEach(p -> studentsCourses.create(checkStudent(p), courseList.get(randomData.randomNumber(0, 19))));
    }

    private Student checkStudent(Student student) {
        if (courseSize.get(student) >= 1 && courseSize.get(student) <= 3) {
            courseSize.put(student, courseSize.get(student) + 1);
            return student;
        } else return studentList.get(randomNumber);
    }
}
