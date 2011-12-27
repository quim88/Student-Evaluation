-- Joaquim Costa, Nov. 14, 2011
-- project5
-- script to load data into project5 database

DELETE FROM Program;

-- data for the Program Table
INSERT INTO Program VALUES
('CSCI','Computer Science'),
('MATH','Mathematics'),
('ECON','Economy'),
('MGT','Management'),
('MKT','Marketing'),
('PHIL','Philosophy'),
('PHYS','Physics'),
('ACCT','Accounting'),
('CIS','Computer Information Systems'),
('FIN','Finance'),
('BIOL','Biology'),
('CHEM','Chemestry'),
('ENGL','English'),
('HIST','History'),
('SOC','Sociology');

INSERT INTO Term VALUES
(91,'Fall',2009),
(92,'Spring',2009),
(93,'Summer',2009),
(101,'Fall',2010),
(102,'Spring',2010),
(103,'Summer',2010),
(111,'Fall',2011),
(112,'Spring',2011),
(113,'Summer',2011),
(121,'Fall',2012),
(122,'Spring',2012),
(123,'Summer',2012),
(131,'Fall',2013),
(132,'Spring',2013),
(133,'Summer',2013);

-- data for the Course Table
INSERT INTO Course VALUES
('ACCT',201,'Principles of Accounting I: Financial',3),
('ACCT',202,'Principles of Accounting II: Managerial',3),
('ACCT',310,'Accounting Systems and Concepts',3),
('ACCT',311,'External Reporting I',3),
('ACCT',312,'External Reporting II',3),
('ACCT',321,'Cost Management I',3),
('ACCT',331,'Federal Income Taxation',3),
('ACCT',351,'Fraud Examination',3),
('ACCT',353,'Accounting for Governmental and Not-for-Profit Organizations',3),
('ACCT',422,'Cost Management II',3),
('ACCT',432,'Advanced Studies in Taxation',3),
('ACCT',441,'Auditing',3),
('ACCT',443,'Business Law',3),
('ACCT',451,'Advanced Financial Accounting',3),
('ACCT',461,'Seminar in Accounting Theory and Practice',3),
('BIOL',111,'Introductory Biology I',4),
('BIOL',112,'Introductory Biology II',4),
('BIOL',221,'Genetics',4),
('BIOL',241,'Biology Research Colloquium',1),
('BIOL',300,'Developmental Biology of Animals',4),
('BIOL',318,'Ecology',4),
('BIOL',320,'Cell and Molecular Biology',4),
('BIOL',321,'Invertebrate Zoology',4),
('BIOL',324,'Vertebrate Zoology',4),
('BIOL',329,'Comparative Vertebrate Anatomy',4),
('BIOL',353,'The Plant Kingdom',4),
('BIOL',354,'Plant Growth and Development',4),
('BIOL',460,'Biology Senior Seminar',3);

-- data for the Student Table and the StudentCourse will be entered 

INSERT INTO Student(fname, lname, major, gradYear, studentType)  VALUES('Joaquim', 'Costa', 'Computer Science', 2013, 'Junior');
-- when i design the java gui and implement it using this database.

INSERT INTO StudentCourse(courseprgm, coursenum, studentid, termid, unit, grade, taken) VALUES
('ACCT',201, 1, 111, 3, 4.00, true);

