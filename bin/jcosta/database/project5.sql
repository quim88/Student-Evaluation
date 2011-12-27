-- project5.sql, Student Course Database
-- CS455, Database System
-- Professor McDowell
-- Joaquim Costa, Nov. 14, 2011

-- connect to the database
CONNECT 'jdbc:derby:cs455_jcosta;create=true';

-- DROP ALL Tables before creating them
DROP TABLE StudentCourse;
DROP TABLE Course;
DROP TABLE Term;
DROP TABLE Program;
DROP TABLE Student;

-- This table stores information about the student
CREATE TABLE Student(
id INT GENERATED ALWAYS AS IDENTITY,
fname VARCHAR(20),
lname VARCHAR(20),
major VARCHAR(50),
gradYear INT,
studentType INT,
PRIMARY KEY(id)
);

-- this table stores info about each program
CREATE TABLE Program(
code CHAR(4),
name VARCHAR(50),
PRIMARY KEY(code)
);

-- this table stores information about all courses offered by the school
-- (not really)
CREATE TABLE Course(
prgm CHAR(4) REFERENCES Program(code),
num INT,
name VARCHAR(100),
unit INT,
PRIMARY KEY(prgm,num)
);

-- this table stores information about the term
-- FALL (1), SPRING(2), SUMMER(3) 
-- code for id is YY + semster digit
-- example id:122 means, spring 2012
CREATE TABLE Term(
id INT,
semester VARCHAR(20),
yr INT,
PRIMARY KEY(id)
);

-- this table stores information about student course
CREATE TABLE StudentCourse(
scid INT GENERATED ALWAYS AS IDENTITY,
courseprgm CHAR(4) NOT NULL,
coursenum INT NOT NULL,
studentid INT REFERENCES Student(id),
termid INT REFERENCES Term(id),
unit INT,
grade DOUBLE,
taken BOOLEAN,
CONSTRAINT sc_fk FOREIGN KEY(courseprgm, coursenum) 
	REFERENCES Course(prgm, num),
PRIMARY KEY(scid)
);

-- load data
--run 'project5data.sql';



