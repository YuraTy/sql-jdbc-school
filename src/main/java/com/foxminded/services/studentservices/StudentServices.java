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

    private  StudentDaoImpl studentDao = new StudentDaoImpl() ;
    private final GroupDaoImpl groupDao = new GroupDaoImpl() ;
    private final RandomData randomData = new RandomData() ;
    private static final Map<Group, Integer> sizeCount = new HashMap<>();
    private final int randomNumber = (int) (Math.random() * 19);


    public void fillingStudentsDB() {
        List<Student> studentList = randomData.randomStudent();
        List<Group> groupList = groupDao.findAll();
        studentList.stream()
                .peek(p -> p.setGroupId(checkGroup(groupList.get(randomNumber)).getGroupId()))
                .forEach(studentDao::create);
    }

    private Group checkGroup(Group group) {
        List<Group> groupList = groupDao.findAll();
        if (!sizeCount.containsKey(group)){
            sizeCount.put(group,1);
            return group;
        }else if(sizeCount.get(group) >= 10 && sizeCount.get(group) <= 30) {
            sizeCount.put(group, sizeCount.get(group) + 1);
            return group;
        } else return groupList.get(randomNumber);
    }

    public void newStudent (Student student) {
        studentDao.create(student);
    }

    public void deleteStudent (int idStudent){
        studentDao.delete(idStudent);
    }

    public void addStudentOnCourse (Student student , Course course){
        studentDao.createTableCourses(student,course);
    }



}
