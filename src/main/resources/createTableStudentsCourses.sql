CREATE TABLE studentsCourses(
StudentId int NOT NULL,
CourseId int NOT NULL,
FOREIGN KEY (StudentId) REFERENCES students(StudentId),
FOREIGN KEY (CourseId) REFERENCES courses(CourseId)
UNIQUE (students,courses));