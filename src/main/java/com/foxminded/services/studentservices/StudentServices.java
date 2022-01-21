package com.foxminded.services.studentservices;

import com.foxminded.course.Course;
import com.foxminded.dao.groupsdao.GroupDaoImpl;
import com.foxminded.dao.studentdao.StudentDaoImpl;
import com.foxminded.groups.Group;
import com.foxminded.randomdata.RandomData;
import com.foxminded.student.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentServices {

    private  StudentDaoImpl studentDao = new StudentDaoImpl();
    private final GroupDaoImpl groupDao = new GroupDaoImpl();
    private final RandomData randomData = new RandomData();
    private static final Map<Group, Integer> sizeCount = new HashMap<>();
    private final int randomNumber = (int) (Math.random() * 19);
    private static final int STUDENTS_IN_GROUP_MIN = 10;
    private static final int STUDENTS_IN_GROUP_MAX = 30;

    public void fillingStudentsDB() {
        List<Student> studentList = randomData.randomStudent();
        List<Group> groupList = groupDao.findAll();
        studentList.stream()
                .peek(p -> p.setGroupId(checkGroup(groupList.get(randomNumber)).getGroupId()))
                .forEach(studentDao::create);
    }

    private Group checkGroup(Group group) {
        List<Group> groupList = groupDao.findAll();
        if (!sizeCount.containsKey(group)) {
            sizeCount.put(group, 1);
            return group;
        } else if (sizeCount.get(group) >= STUDENTS_IN_GROUP_MIN && sizeCount.get(group) <= STUDENTS_IN_GROUP_MAX) {
            sizeCount.put(group, sizeCount.get(group) + 1);
            return group;
        } else return groupList.get(randomNumber);
    }

    public void newStudent(Student student) {
        studentDao.create(student);
    }

    public void deleteStudent(int idStudent) {
        studentDao.delete(idStudent);
    }

    public void addStudentForCourse(int studentId, int courseId) {
        studentDao.createTableCourses(studentId, courseId);
    }

    public void deleteCourseForStudent(int studentId, int courseId) {
        studentDao.deleteStudentFromCourse(studentId, courseId);
    }

    public List<Student> findStudentsByCourse(String namedCourse) {
        return studentDao.findStudentsByCourse(namedCourse);
    }

    public List<Student> getAllStudent() {
        return studentDao.findAll();
    }
}
