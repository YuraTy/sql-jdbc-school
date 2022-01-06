package com.foxminded.dao.coursesdao;

import com.foxminded.course.Course;

import java.util.List;

public interface CourseDao {

    void create(Course course);

    List<Course> findAll();

    Course findId(int courseId);

    void update(Course course, int courseId);

    void delete(int courseId);
}
