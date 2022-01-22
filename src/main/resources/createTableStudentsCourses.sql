CREATE TABLE StudentsCourses(
StudentId int NOT NULL,
CourseId int NOT NULL,
FOREIGN KEY (StudentId) REFERENCES Students(StudentId),
FOREIGN KEY (CourseId) REFERENCES Courses(CourseId)
UNIQUE (Students,Courses));