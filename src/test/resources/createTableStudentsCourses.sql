CREATE TABLE StudentsCourses(
StudentId INT REFERENCES Students(StudentId),
CourseId INT REFERENCES Courses(CourseId),
CONSTRAINT StudentsCoursesId PRIMARY KEY (StudentId,CourseId)
);