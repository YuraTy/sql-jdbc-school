package com.foxminded.dao.daostudent;

import com.foxminded.student.Student;

import java.io.IOException;
import java.sql.SQLException;

public interface DaoStudent {
    void create (String first_name , String last_name , int group_id) throws SQLException, IOException;
    String findAll() throws SQLException, IOException;
    String find_id(int student_id) throws SQLException, IOException;
    void update (int group_id ,String  first_name ,String last_name , int student_id) throws SQLException, IOException;
    void delete (int student_id ) throws SQLException, IOException;
}
