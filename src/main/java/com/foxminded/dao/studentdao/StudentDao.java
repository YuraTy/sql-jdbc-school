package com.foxminded.dao.studentdao;

import com.foxminded.student.Student;

import java.util.List;

public interface StudentDao {

        void create (int studentId , String first_name , String last_name , int group_id);
        List<Student> findAll();
        Student findId(int student_id);
        void update (int group_id ,String  first_name ,String last_name , int student_id);
        void delete (int student_id );
}
