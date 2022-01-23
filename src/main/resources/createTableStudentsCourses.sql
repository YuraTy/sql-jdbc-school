CREATE TABLE students_courses(
student_id INT REFERENCES students(student_id),
course_id INT REFERENCES courses(course_id),
CONSTRAINT students_coursesId PRIMARY KEY (student_id,course_id)
);