package com.foxminded.dao.daostudent;

import com.foxminded.datasource.DataSource;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringJoiner;

public class DaoStudentImp implements DaoStudent{

    private DataSource dataSource = new DataSource();
    private static int student_id = 0;

    private static final String FIND_BY_Student_id = "SELECT group_id , first_name , last_name FROM students WHERE student_id = ? ";
    private static final String UPDATE_BY_Student_id = "UPDATE students SET group_id = ? , first_name = ? , last_name = ? WHERE student_id = ?";

    @Override
    public void create(String first_name , String last_name , int group_id) throws SQLException, IOException {
        dataSource.getConnection().createStatement().executeUpdate("INSERT INTO students VALUES (student_id +=1 , group_id, first_name , last_name)");
    }

    @Override
    public String findAll() throws SQLException, IOException {
        ResultSet resultSet = dataSource.getConnection().createStatement().executeQuery("SELECT * FROM students");
        StringJoiner resultJoiner = new StringJoiner("\n");
        while (resultSet.next()){
            resultJoiner.add(resultSet.getInt(1) + resultSet.getInt(2) + resultSet.getString(3) + resultSet.getString(4));
        }
        return resultJoiner.toString();
    }

    @Override
    public String find_id(int student_id) throws SQLException, IOException {
        dataSource.getConnection().createStatement().executeUpdate(FIND_BY_Student_id,student_id);
        return null;
    }

    @Override
    public void update(int group_id ,String  first_name ,String last_name , int student_id) throws SQLException, IOException {
        dataSource.getConnection().createStatement().executeUpdate(UPDATE_BY_Student_id,group_id,first_name,last_name,student_id);
    }

    @Override
    public void delete(int student_id )throws SQLException, IOException {
        dataSource.getConnection().createStatement().executeUpdate("DELETE FROM students WHERE student_id =" + student_id);
    }
}
