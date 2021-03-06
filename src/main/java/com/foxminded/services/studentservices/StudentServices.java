package com.foxminded.services.studentservices;

import com.foxminded.course.Course;
import com.foxminded.dao.groupsdao.GroupDaoImpl;
import com.foxminded.dao.studentdao.StudentDaoImpl;
import com.foxminded.groups.Group;
import com.foxminded.randomdata.RandomData;
import com.foxminded.student.Student;

import java.io.IOException;
import java.sql.SQLException;
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

    public void addStudentForCourse(Student student , Course course) {
        studentDao.addStudentToCourse(student, course);
    }

    public void deleteCourseForStudent(Student student , Course course) {
        studentDao.deleteStudentFromCourse(student, course);
    }

    public List<Student> findStudentsByCourse(Course course) {
        return studentDao.findStudentsByCourse(course);
    }

    public List<Student> getAllStudent() {
        return studentDao.findAll();
    }

    public void createTableStudents() throws SQLException, IOException {
        studentDao.createTableStudents();
    }

    public void deleteTableStudents() throws SQLException, IOException {
        studentDao.deleteTableStudents();
    }

    public void createTableCourseStudent() throws SQLException, IOException {
        studentDao.createTableCourseStudent();
    }

    public void deleteTableCourseStudent() throws SQLException, IOException {
        studentDao.deleteTableCourseStudent();
    }
}
