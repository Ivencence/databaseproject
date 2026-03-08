DROP DATABASE IF EXISTS companydb;
CREATE DATABASE companydb;
USE companydb;


CREATE TABLE Departments (
                             dept_id INT PRIMARY KEY,
                             name VARCHAR(100) NOT NULL UNIQUE,
                             manager_id INT
);

CREATE TABLE Employees (
                           emp_id INT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           age INT CHECK (age >= 18),
                           email VARCHAR(100) NOT NULL UNIQUE,
                           dept_id INT NOT NULL,
                           FOREIGN KEY (dept_id) REFERENCES Departments(dept_id)
);

CREATE TABLE Managers (
                          emp_id INT PRIMARY KEY,
                          dept_id INT UNIQUE,
                          FOREIGN KEY (emp_id) REFERENCES Employees(emp_id),
                          FOREIGN KEY (dept_id) REFERENCES Departments(dept_id)
);

ALTER TABLE Departments
    ADD FOREIGN KEY (manager_id) REFERENCES Employees(emp_id);

CREATE TABLE Clients (
                         client_id INT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE
);

CREATE TABLE Projects (
                          pr_id INT PRIMARY KEY,
                          title VARCHAR(100) NOT NULL,
                          client_id INT NOT NULL,
                          FOREIGN KEY (client_id) REFERENCES Clients(client_id)
);

CREATE TABLE Project_Employees (
                                   pr_id INT,
                                   emp_id INT,
                                   PRIMARY KEY (pr_id, emp_id),
                                   FOREIGN KEY (pr_id) REFERENCES Projects(pr_id) ON DELETE CASCADE,
                                   FOREIGN KEY (emp_id) REFERENCES Employees(emp_id) ON DELETE CASCADE
);

CREATE TABLE Resources (
                           res_id INT PRIMARY KEY,
                           material VARCHAR(100) NOT NULL,
                           quantity INT CHECK (quantity > 0)
);

CREATE TABLE Project_Resources (
                                   pr_id INT,
                                   res_id INT,
                                   PRIMARY KEY (pr_id, res_id),
                                   FOREIGN KEY (pr_id) REFERENCES Projects(pr_id),
                                   FOREIGN KEY (res_id) REFERENCES Resources(res_id)
);

CREATE TABLE Documents (
                           doc_id INT PRIMARY KEY,
                           title VARCHAR(100) NOT NULL,
                           pr_id INT NOT NULL,
                           FOREIGN KEY (pr_id) REFERENCES Projects(pr_id)
);

CREATE TABLE Users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       date_of_birth DATE,
                       role VARCHAR(20) DEFAULT 'USER',
                       photo LONGBLOB
);

INSERT INTO Departments VALUES
                            (1, 'Design', NULL),
                            (2, 'Engineering', NULL),
                            (3, 'Financial Affairs', NULL);

INSERT INTO Employees VALUES
                          (101, 'Anna Ivanova', 30, 'a.ivanova@gmail.com', 1),
                          (102, 'Martin Markov', 28, 'm.markov@gmail.com', 2),
                          (103, 'Maria Zhekova', 35, 'm.zhekova@gmail.com', 3);

UPDATE Departments SET manager_id = 101 WHERE dept_id = 1;
UPDATE Departments SET manager_id = 102 WHERE dept_id = 2;
UPDATE Departments SET manager_id = 103 WHERE dept_id = 3;

INSERT INTO Managers VALUES
                         (101,1),
                         (102,2),
                         (103,3);

INSERT INTO Clients VALUES
                        (201, 'Georgi Stanchev', 'g.stanchev@gmail.com'),
                        (202, 'Raiffeisen Bank', 'user@raiffeisen.com'),
                        (203, 'Rumyana Ivanova', 'r.ivanova@gmail.com');

INSERT INTO Projects VALUES
                         (301, 'Dianabat Two Storey House', 201),
                         (302, 'Boulevard Hristo Botev Office Building', 202),
                         (303, 'Dragalevtsi One Storey Villa', 203);

INSERT INTO Project_Employees VALUES
                                  (301, 101),
                                  (302, 102),
                                  (303, 103);

INSERT INTO Resources VALUES
                          (401, 'Laptop', 10),
                          (402, 'Projector', 2),
                          (403, 'Whiteboard', 5);

INSERT INTO Project_Resources VALUES
                                  (301, 401),
                                  (302, 402),
                                  (303, 403);

INSERT INTO Documents VALUES
                          (501, 'Architectural Plans', 301),
                          (502, 'Electricity Network', 302),
                          (503, 'Estimated Costs', 303);

INSERT INTO users (username,password,firstName,lastName,dateOfBirth,age,address,photo) VALUES
('user1','pass123','Ivan','Petrov','1995-02-12',29,'Sofia',NULL),
('user2','pass123','Maria','Georgieva','1994-07-21',30,'Plovdiv',NULL),
('user3','pass123','Petar','Ivanov','1990-11-05',34,'Varna',NULL),
('user4','pass123','Nikolay','Dimitrov','1992-04-18',32,'Burgas',NULL),
('user5','pass123','Anna','Koleva','1996-03-30',28,'Ruse',NULL),
('user6','pass123','Georgi','Stoyanov','1993-09-12',31,'Sofia',NULL),
('user7','pass123','Elena','Petrova','1997-01-20',27,'Plovdiv',NULL),
('user8','pass123','Martin','Todorov','1991-08-08',33,'Varna',NULL),
('user9','pass123','Desislava','Ivanova','1998-05-14',26,'Burgas',NULL),
('user10','pass123','Hristo','Kolev','1990-12-02',34,'Sofia',NULL),

('user11','pass123','Stefan','Dimitrov','1994-03-11',30,'Ruse',NULL),
('user12','pass123','Viktoria','Georgieva','1995-06-19',29,'Varna',NULL),
('user13','pass123','Daniel','Petrov','1993-02-09',31,'Plovdiv',NULL),
('user14','pass123','Simona','Kostova','1997-07-25',27,'Sofia',NULL),
('user15','pass123','Kristian','Ivanov','1991-10-01',33,'Burgas',NULL),
('user16','pass123','Teodora','Nikolova','1996-11-17',28,'Varna',NULL),
('user17','pass123','Borislav','Todorov','1992-04-28',32,'Ruse',NULL),
('user18','pass123','Radostina','Petrova','1995-09-09',29,'Plovdiv',NULL),
('user19','pass123','Dimitar','Kolev','1990-06-13',34,'Sofia',NULL),
('user20','pass123','Kalina','Dimitrova','1998-02-22',26,'Varna',NULL),

('user21','pass123','Ivaylo','Stoyanov','1994-01-15',30,'Burgas',NULL),
('user22','pass123','Milena','Ivanova','1997-08-03',27,'Plovdiv',NULL),
('user23','pass123','Anton','Petrov','1992-12-07',32,'Sofia',NULL),
('user24','pass123','Yana','Georgieva','1996-04-19',28,'Ruse',NULL),
('user25','pass123','Stanislav','Kolev','1991-05-21',33,'Varna',NULL),
('user26','pass123','Gabriela','Dimitrova','1995-10-11',29,'Sofia',NULL),
('user27','pass123','Valentin','Petrov','1993-06-01',31,'Plovdiv',NULL),
('user28','pass123','Lilia','Ivanova','1998-03-29',26,'Burgas',NULL),
('user29','pass123','Kiril','Todorov','1994-07-14',30,'Varna',NULL),
('user30','pass123','Polina','Koleva','1997-11-16',27,'Ruse',NULL),

('user31','pass123','Rosen','Dimitrov','1992-02-26',32,'Sofia',NULL),
('user32','pass123','Svetla','Petrova','1996-09-05',28,'Varna',NULL),
('user33','pass123','Vasil','Ivanov','1990-08-30',34,'Plovdiv',NULL),
('user34','pass123','Kristina','Georgieva','1995-12-18',29,'Burgas',NULL),
('user35','pass123','Aleksandar','Stoyanov','1991-03-02',33,'Ruse',NULL),
('user36','pass123','Petya','Dimitrova','1998-06-24',26,'Varna',NULL),
('user37','pass123','Emil','Kolev','1993-05-09',31,'Sofia',NULL),
('user38','pass123','Tanya','Ivanova','1997-01-27',27,'Plovdiv',NULL),
('user39','pass123','Lyubomir','Petrov','1994-04-06',30,'Burgas',NULL),
('user40','pass123','Silvia','Georgieva','1996-10-13',28,'Varna',NULL),

('user41','pass123','Kamen','Dimitrov','1992-07-20',32,'Sofia',NULL),
('user42','pass123','Nina','Koleva','1995-03-12',29,'Ruse',NULL),
('user43','pass123','Boris','Ivanov','1990-01-04',34,'Plovdiv',NULL),
('user44','pass123','Zornitsa','Petrova','1997-09-15',27,'Varna',NULL),
('user45','pass123','Pavel','Georgiev','1993-02-28',31,'Burgas',NULL),
('user46','pass123','Ralitsa','Dimitrova','1996-06-07',28,'Sofia',NULL),
('user47','pass123','Nikol','Koleva','1998-11-23',26,'Plovdiv',NULL),
('user48','pass123','Simeon','Ivanov','1994-05-30',30,'Varna',NULL),
('user49','pass123','Yordan','Petrov','1992-08-17',32,'Burgas',NULL),
('user50','pass123','Mariya','Georgieva','1995-12-09',29,'Sofia',NULL);
