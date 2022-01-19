package com.foxminded.services.courseservices;

import com.foxminded.course.Course;
import com.foxminded.dao.coursesdao.CourseDaoImpl;
import com.foxminded.dao.studentdao.StudentDaoImpl;
import com.foxminded.randomdata.RandomData;
import com.foxminded.student.Student;

import java.util.*;

public class CourseServices {

    private  CourseDaoImpl courseDao;
    private final RandomData randomData = new RandomData();
    private  StudentDaoImpl studentDao ;
    private static final Map<Student, Integer> courseSize = new HashMap<>();
    private final int randomNumber = COURSE_FOR_STUDENTS_MIN + (int) (Math.random() * COURSE_FOR_STUDENTS_MAX) ;

    private static final int COURSE_FOR_STUDENTS_MIN = 1;
    private static final int COURSE_FOR_STUDENTS_MAX = 3;

    public void fillingCoursesDB() {
        randomData.randomCourses().forEach(courseDao::create);
    }

    public void fillingStudentsCourses() {
        List<Student> studentList = studentDao.findAll();
        List<Course> courseList = courseDao.findAll();
        studentList.forEach(p -> studentDao.createTableCourses(checkStudent(p), courseList.get((int) (Math.random() * 19))));
    }

    private Student checkStudent(Student student) {
        List<Student> studentList = studentDao.findAll();
        if (!courseSize.containsKey(student)) {
            courseSize.put(student, 1);
            return student;
        }else if (courseSize.get(student) >= COURSE_FOR_STUDENTS_MIN && courseSize.get(student) <= COURSE_FOR_STUDENTS_MAX) {
            courseSize.put(student, courseSize.get(student) + 1);
            return student;
        } else return studentList.get(randomNumber);
    }
}
