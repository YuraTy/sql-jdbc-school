package com.foxminded.dao.studentdao;

import com.foxminded.student.Student;

import java.util.List;

public interface StudentDao {

        void create (Student student);
        List<Student> findAll();
        Student findId(int student_id);
        void update (Student student , int studentId);
        void delete (int student_id );
}
