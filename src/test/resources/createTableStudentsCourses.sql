CREATE TABLE studentsCourses(
StudentId INT REFERENCES students(StudentId),
CourseId INT REFERENCES courses(CourseId),
CONSTRAINT StudentsCoursesId PRIMARY KEY (StudentId,CourseId)
);