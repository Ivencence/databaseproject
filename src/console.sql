USE architecture;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS Project_Resources;
DROP TABLE IF EXISTS Project_Employees;
DROP TABLE IF EXISTS Documents;
DROP TABLE IF EXISTS Resources;
DROP TABLE IF EXISTS Projects;
DROP TABLE IF EXISTS Clients;
DROP TABLE IF EXISTS Employees;
DROP TABLE IF EXISTS Departments;
DROP TABLE IF EXISTS Users;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE Users (
                       id            INT AUTO_INCREMENT PRIMARY KEY,
                       username      VARCHAR(50)  UNIQUE NOT NULL,
                       password      VARCHAR(255) NOT NULL,
                       first_name    VARCHAR(100) NOT NULL,
                       last_name     VARCHAR(100) NOT NULL,
                       email         VARCHAR(100) UNIQUE NOT NULL,
                       date_of_birth DATE,
                       role          VARCHAR(100) DEFAULT 'USER',
                       photo         LONGBLOB
);

CREATE TABLE Departments (
                             dept_id    INT AUTO_INCREMENT PRIMARY KEY,
                             name       VARCHAR(100) NOT NULL UNIQUE,
                             manager_id INT NULL
);

CREATE TABLE Employees (
                           emp_id   INT AUTO_INCREMENT PRIMARY KEY,
                           name     VARCHAR(100) NOT NULL,
                           age      TINYINT UNSIGNED NOT NULL CHECK (age >= 18),
                           email    VARCHAR(150) NOT NULL UNIQUE,
                           dept_id  INT NOT NULL,
                           user_id  INT UNIQUE,
                           photo    LONGBLOB,
                           FOREIGN KEY (dept_id) REFERENCES Departments(dept_id),
                           FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

ALTER TABLE Departments
    ADD CONSTRAINT fk_dept_manager
        FOREIGN KEY (manager_id) REFERENCES Employees(emp_id);

CREATE TABLE Clients (
                         client_id INT AUTO_INCREMENT PRIMARY KEY,
                         name      VARCHAR(100) NOT NULL,
                         email     VARCHAR(150) UNIQUE,
                         user_id   INT UNIQUE,
                         photo     LONGBLOB,
                         FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Projects (
                          pr_id     INT AUTO_INCREMENT PRIMARY KEY,
                          title     VARCHAR(200)  NOT NULL,
                          client_id INT           NOT NULL,
                          location  VARCHAR(200)  DEFAULT '',
                          budget    DECIMAL(12,2) NOT NULL DEFAULT 0.00,
                          FOREIGN KEY (client_id) REFERENCES Clients(client_id)
);

CREATE TABLE Project_Employees (
                                   pr_id  INT,
                                   emp_id INT,
                                   PRIMARY KEY (pr_id, emp_id),
                                   FOREIGN KEY (pr_id)  REFERENCES Projects(pr_id)   ON DELETE CASCADE,
                                   FOREIGN KEY (emp_id) REFERENCES Employees(emp_id) ON DELETE CASCADE
);

-- ── Data ─────────────────────────────────────────────────────────────────────

INSERT INTO Users (id, username, password, first_name, last_name, email, date_of_birth, role) VALUES
                                                                                                  (1,  'admin',    '123', 'System',   'Admin',       'admin@arch.com',      '1980-01-01', 'ADMIN'),
                                                                                                  (2,  'emp1',     '123', 'Alice',    'Johnson',     'alice@arch.com',      '1990-03-12', 'EMPLOYEE'),
                                                                                                  (3,  'emp2',     '123', 'Robert',   'Smith',       'robert@arch.com',     '1988-07-04', 'EMPLOYEE'),
                                                                                                  (4,  'emp3',     '123', 'Maria',    'Ivanova',     'maria@arch.com',      '1992-11-23', 'EMPLOYEE'),
                                                                                                  (5,  'emp4',     '123', 'John',     'Brown',       'john@arch.com',       '1985-05-17', 'EMPLOYEE'),
                                                                                                  (6,  'emp5',     '123', 'Emma',     'Wilson',      'emma@arch.com',       '1993-09-08', 'EMPLOYEE'),
                                                                                                  (7,  'emp6',     '123', 'David',    'Miller',      'david@arch.com',      '1987-02-14', 'EMPLOYEE'),
                                                                                                  (8,  'emp7',     '123', 'Sophia',   'Davis',       'sophia@arch.com',     '1991-06-30', 'EMPLOYEE'),
                                                                                                  (9,  'emp8',     '123', 'James',    'Taylor',      'james@arch.com',      '1986-12-05', 'EMPLOYEE'),
                                                                                                  (10, 'emp9',     '123', 'Olivia',   'Anderson',    'olivia@arch.com',     '1994-04-19', 'EMPLOYEE'),
                                                                                                  (11, 'emp10',    '123', 'William',  'Thomas',      'william@arch.com',    '1989-08-22', 'EMPLOYEE'),
                                                                                                  (12, 'emp11',    '123', 'Daniel',   'Moore',       'daniel@arch.com',     '1995-01-11', 'EMPLOYEE'),
                                                                                                  (13, 'emp12',    '123', 'Emily',    'White',       'emily@arch.com',      '1990-10-03', 'EMPLOYEE'),
                                                                                                  (14, 'emp13',    '123', 'Michael',  'Harris',      'michael@arch.com',    '1983-07-28', 'EMPLOYEE'),
                                                                                                  (15, 'emp14',    '123', 'Sofia',    'Martin',      'sofia@arch.com',      '1996-03-16', 'EMPLOYEE'),
                                                                                                  (16, 'emp15',    '123', 'Ethan',    'Clark',       'ethan@arch.com',      '1988-11-09', 'EMPLOYEE'),
                                                                                                  (17, 'emp16',    '123', 'Isabella', 'Lewis',       'isabella@arch.com',   '1992-06-25', 'EMPLOYEE'),
                                                                                                  (18, 'emp17',    '123', 'Liam',     'Walker',      'liam@arch.com',       '1984-09-14', 'EMPLOYEE'),
                                                                                                  (19, 'emp18',    '123', 'Mia',      'Hall',        'mia@arch.com',        '1997-02-07', 'EMPLOYEE'),
                                                                                                  (20, 'emp19',    '123', 'Noah',     'Allen',       'noah@arch.com',       '1991-05-31', 'EMPLOYEE'),
                                                                                                  (21, 'emp20',    '123', 'Ava',      'Young',       'ava@arch.com',        '1993-12-18', 'EMPLOYEE'),
                                                                                                  (22, 'client1',  '123', 'Thomas',   'Baker',       'tbaker@mail.com',     '1978-04-02', 'CLIENT'),
                                                                                                  (23, 'client2',  '123', 'Laura',    'Gonzalez',    'lgonzalez@mail.com',  '1982-08-15', 'CLIENT'),
                                                                                                  (24, 'client3',  '123', 'Kevin',    'Nelson',      'knelson@mail.com',    '1975-01-27', 'CLIENT'),
                                                                                                  (25, 'client4',  '123', 'Rachel',   'Carter',      'rcarter@mail.com',    '1980-11-03', 'CLIENT'),
                                                                                                  (26, 'client5',  '123', 'Brian',    'Mitchell',    'bmitchell@mail.com',  '1977-06-19', 'CLIENT'),
                                                                                                  (27, 'client6',  '123', 'Angela',   'Perez',       'aperez@mail.com',     '1983-03-08', 'CLIENT'),
                                                                                                  (28, 'client7',  '123', 'Steven',   'Roberts',     'sroberts@mail.com',   '1979-09-22', 'CLIENT'),
                                                                                                  (29, 'client8',  '123', 'Karen',    'Turner',      'kturner@mail.com',    '1985-07-11', 'CLIENT'),
                                                                                                  (30, 'client9',  '123', 'Paul',     'Phillips',    'pphillips@mail.com',  '1972-12-30', 'CLIENT'),
                                                                                                  (31, 'client10', '123', 'Sandra',   'Campbell',    'scampbell@mail.com',  '1981-05-04', 'CLIENT'),
                                                                                                  (32, 'client11', '123', 'Mark',     'Parker',      'mparker@mail.com',    '1976-02-17', 'CLIENT'),
                                                                                                  (33, 'client12', '123', 'Betty',    'Evans',       'bevans@mail.com',     '1984-10-09', 'CLIENT'),
                                                                                                  (34, 'client13', '123', 'Donald',   'Edwards',     'dedwards@mail.com',   '1973-08-26', 'CLIENT'),
                                                                                                  (35, 'client14', '123', 'Dorothy',  'Collins',     'dcollins@mail.com',   '1986-04-13', 'CLIENT'),
                                                                                                  (36, 'client15', '123', 'George',   'Stewart',     'gstewart@mail.com',   '1978-01-05', 'CLIENT'),
                                                                                                  (37, 'client16', '123', 'Lisa',     'Sanchez',     'lsanchez@mail.com',   '1982-07-21', 'CLIENT'),
                                                                                                  (38, 'client17', '123', 'Kenneth',  'Morris',      'kmorris@mail.com',    '1975-11-14', 'CLIENT'),
                                                                                                  (39, 'client18', '123', 'Nancy',    'Rogers',      'nrogers@mail.com',    '1980-03-29', 'CLIENT'),
                                                                                                  (40, 'client19', '123', 'Edward',   'Reed',        'ereed@mail.com',      '1977-09-06', 'CLIENT'),
                                                                                                  (41, 'client20', '123', 'Carol',    'Cook',        'ccook@mail.com',      '1983-06-18', 'CLIENT'),
                                                                                                  (42, 'client21', '123', 'Ronald',   'Morgan',      'rmorgan@mail.com',    '1979-02-11', 'CLIENT'),
                                                                                                  (43, 'client22', '123', 'Sharon',   'Bell',        'sbell@mail.com',      '1985-12-03', 'CLIENT'),
                                                                                                  (44, 'client23', '123', 'Anthony',  'Murphy',      'amurphy@mail.com',    '1972-08-07', 'CLIENT'),
                                                                                                  (45, 'client24', '123', 'Helen',    'Bailey',      'hbailey@mail.com',    '1981-04-24', 'CLIENT'),
                                                                                                  (46, 'client25', '123', 'Charles',  'Rivera',      'crivera@mail.com',    '1976-10-16', 'CLIENT'),
                                                                                                  (47, 'client26', '123', 'Donna',    'Cooper',      'dcooper@mail.com',    '1984-07-02', 'CLIENT'),
                                                                                                  (48, 'client27', '123', 'Joseph',   'Richardson',  'jrichardson@mail.com','1973-03-19', 'CLIENT'),
                                                                                                  (49, 'client28', '123', 'Michelle', 'Cox',         'mcox@mail.com',       '1980-01-08', 'CLIENT'),
                                                                                                  (50, 'client29', '123', 'Thomas',   'Howard',      'thoward@mail.com',    '1977-11-25', 'CLIENT'),
                                                                                                  (51, 'client30', '123', 'Amanda',   'Ward',        'award@mail.com',      '1983-05-12', 'CLIENT');

INSERT INTO Departments (dept_id, name, manager_id) VALUES
                                                        (1, 'Architecture',   NULL),
                                                        (2, 'Engineering',    NULL),
                                                        (3, 'Design',         NULL),
                                                        (4, 'Construction',   NULL),
                                                        (5, 'Administration', NULL);

INSERT INTO Employees (emp_id, name, age, email, dept_id, user_id) VALUES
                                                                       (1,  'Alice Johnson',   34, 'alice@arch.com',    1, 2),
                                                                       (2,  'Robert Smith',    36, 'robert@arch.com',   2, 3),
                                                                       (3,  'Maria Ivanova',   32, 'maria@arch.com',    3, 4),
                                                                       (4,  'John Brown',      39, 'john@arch.com',     4, 5),
                                                                       (5,  'Emma Wilson',     31, 'emma@arch.com',     5, 6),
                                                                       (6,  'David Miller',    37, 'david@arch.com',    1, 7),
                                                                       (7,  'Sophia Davis',    33, 'sophia@arch.com',   2, 8),
                                                                       (8,  'James Taylor',    38, 'james@arch.com',    3, 9),
                                                                       (9,  'Olivia Anderson', 30, 'olivia@arch.com',   4, 10),
                                                                       (10, 'William Thomas',  35, 'william@arch.com',  5, 11),
                                                                       (11, 'Daniel Moore',    29, 'daniel@arch.com',   1, 12),
                                                                       (12, 'Emily White',     34, 'emily@arch.com',    2, 13),
                                                                       (13, 'Michael Harris',  41, 'michael@arch.com',  3, 14),
                                                                       (14, 'Sofia Martin',    28, 'sofia@arch.com',    4, 15),
                                                                       (15, 'Ethan Clark',     36, 'ethan@arch.com',    5, 16),
                                                                       (16, 'Isabella Lewis',  32, 'isabella@arch.com', 1, 17),
                                                                       (17, 'Liam Walker',     40, 'liam@arch.com',     2, 18),
                                                                       (18, 'Mia Hall',        27, 'mia@arch.com',      3, 19),
                                                                       (19, 'Noah Allen',      33, 'noah@arch.com',     4, 20),
                                                                       (20, 'Ava Young',       31, 'ava@arch.com',      5, 21);

UPDATE Departments SET manager_id = 1  WHERE dept_id = 1;
UPDATE Departments SET manager_id = 2  WHERE dept_id = 2;
UPDATE Departments SET manager_id = 3  WHERE dept_id = 3;
UPDATE Departments SET manager_id = 4  WHERE dept_id = 4;
UPDATE Departments SET manager_id = 5  WHERE dept_id = 5;

INSERT INTO Clients (client_id, name, email, user_id) VALUES
                                                          (1,  'Thomas Baker',      'tbaker@mail.com',      22),
                                                          (2,  'Laura Gonzalez',    'lgonzalez@mail.com',   23),
                                                          (3,  'Kevin Nelson',      'knelson@mail.com',      24),
                                                          (4,  'Rachel Carter',     'rcarter@mail.com',      25),
                                                          (5,  'Brian Mitchell',    'bmitchell@mail.com',    26),
                                                          (6,  'Angela Perez',      'aperez@mail.com',       27),
                                                          (7,  'Steven Roberts',    'sroberts@mail.com',     28),
                                                          (8,  'Karen Turner',      'kturner@mail.com',      29),
                                                          (9,  'Paul Phillips',     'pphillips@mail.com',    30),
                                                          (10, 'Sandra Campbell',   'scampbell@mail.com',    31),
                                                          (11, 'Mark Parker',       'mparker@mail.com',      32),
                                                          (12, 'Betty Evans',       'bevans@mail.com',       33),
                                                          (13, 'Donald Edwards',    'dedwards@mail.com',     34),
                                                          (14, 'Dorothy Collins',   'dcollins@mail.com',     35),
                                                          (15, 'George Stewart',    'gstewart@mail.com',     36),
                                                          (16, 'Lisa Sanchez',      'lsanchez@mail.com',     37),
                                                          (17, 'Kenneth Morris',    'kmorris@mail.com',      38),
                                                          (18, 'Nancy Rogers',      'nrogers@mail.com',      39),
                                                          (19, 'Edward Reed',       'ereed@mail.com',        40),
                                                          (20, 'Carol Cook',        'ccook@mail.com',        41),
                                                          (21, 'Ronald Morgan',     'rmorgan@mail.com',      42),
                                                          (22, 'Sharon Bell',       'sbell@mail.com',        43),
                                                          (23, 'Anthony Murphy',    'amurphy@mail.com',      44),
                                                          (24, 'Helen Bailey',      'hbailey@mail.com',      45),
                                                          (25, 'Charles Rivera',    'crivera@mail.com',      46),
                                                          (26, 'Donna Cooper',      'dcooper@mail.com',      47),
                                                          (27, 'Joseph Richardson', 'jrichardson@mail.com',  48),
                                                          (28, 'Michelle Cox',      'mcox@mail.com',         49),
                                                          (29, 'Thomas Howard',     'thoward@mail.com',      50),
                                                          (30, 'Amanda Ward',       'award@mail.com',        51);

INSERT INTO Projects (pr_id, title, client_id, location, budget) VALUES
                                                                     (1,  'Residential Complex A',   1,  'Sofia',         850000.00),
                                                                     (2,  'Office Tower B',          2,  'Plovdiv',       1200000.00),
                                                                     (3,  'Shopping Mall C',         3,  'Varna',         3500000.00),
                                                                     (4,  'Luxury Villa D',          4,  'Burgas',         620000.00),
                                                                     (5,  'Hotel Project E',         5,  'Nessebar',      4200000.00),
                                                                     (6,  'Apartment Block F',       6,  'Sofia',          950000.00),
                                                                     (7,  'Business Center G',       7,  'Plovdiv',       1800000.00),
                                                                     (8,  'Industrial Facility H',   8,  'Ruse',          2100000.00),
                                                                     (9,  'Urban Housing I',         9,  'Stara Zagora',   760000.00),
                                                                     (10, 'Corporate HQ J',          10, 'Sofia',         5000000.00),
                                                                     (11, 'Residential Complex K',   11, 'Varna',          890000.00),
                                                                     (12, 'Office Building L',       12, 'Burgas',        1100000.00),
                                                                     (13, 'Shopping Centre M',       13, 'Sofia',         2800000.00),
                                                                     (14, 'Luxury Villa N',          14, 'Plovdiv',        580000.00),
                                                                     (15, 'Boutique Hotel O',        15, 'Sozopol',       3100000.00),
                                                                     (16, 'Apartment Block P',       16, 'Sofia',         1050000.00),
                                                                     (17, 'Business Park Q',         17, 'Varna',         2400000.00),
                                                                     (18, 'Warehouse Facility R',    18, 'Ruse',           870000.00),
                                                                     (19, 'Social Housing S',        19, 'Montana',        640000.00),
                                                                     (20, 'Corporate Campus T',      20, 'Sofia',         6800000.00),
                                                                     (21, 'Residential Tower U',     21, 'Plovdiv',       1300000.00),
                                                                     (22, 'Co-Working Space V',      22, 'Sofia',          490000.00),
                                                                     (23, 'Retail Park W',           23, 'Varna',         2200000.00),
                                                                     (24, 'Private Villa X',         24, 'Bansko',         710000.00),
                                                                     (25, 'Resort Complex Y',        25, 'Albena',        5500000.00),
                                                                     (26, 'Mixed-Use Block Z',       26, 'Sofia',         1750000.00),
                                                                     (27, 'Tech Campus AA',          27, 'Sofia',         3900000.00),
                                                                     (28, 'Logistics Centre AB',     28, 'Plovdiv',       1600000.00),
                                                                     (29, 'Eco Housing AC',          29, 'Gabrovo',        820000.00),
                                                                     (30, 'Headquarters AD',         30, 'Sofia',         4400000.00),
                                                                     (31, 'Marina Development AE',   1,  'Varna',         7200000.00),
                                                                     (32, 'School Building AF',      2,  'Sofia',          930000.00),
                                                                     (33, 'Medical Centre AG',       3,  'Plovdiv',       2600000.00),
                                                                     (34, 'Sports Complex AH',       4,  'Burgas',        3300000.00),
                                                                     (35, 'Cultural Centre AI',      5,  'Sofia',         1900000.00),
                                                                     (36, 'University Block AJ',     6,  'Sofia',         2700000.00),
                                                                     (37, 'Parking Structure AK',    7,  'Varna',          580000.00),
                                                                     (38, 'Bridge Project AL',       8,  'Ruse',          8100000.00),
                                                                     (39, 'Road Infrastructure AM',  9,  'Plovdiv',       4700000.00),
                                                                     (40, 'Airport Terminal AN',     10, 'Sofia',        12000000.00),
                                                                     (41, 'Museum Renovation AO',    11, 'Plovdiv',       1400000.00),
                                                                     (42, 'Theatre Extension AP',    12, 'Sofia',         2100000.00),
                                                                     (43, 'Power Plant AQ',          13, 'Varna',         9500000.00),
                                                                     (44, 'Water Treatment AR',      14, 'Ruse',          3800000.00),
                                                                     (45, 'Solar Farm AS',           15, 'Stara Zagora',  6200000.00),
                                                                     (46, 'Data Centre AT',          16, 'Sofia',         5100000.00),
                                                                     (47, 'Cold Storage AU',         17, 'Plovdiv',       1250000.00),
                                                                     (48, 'Residential Estate AV',   18, 'Varna',         3400000.00),
                                                                     (49, 'Green Office AW',         19, 'Sofia',         2900000.00),
                                                                     (50, 'Smart City Hub AX',       20, 'Sofia',         7800000.00);

INSERT INTO Project_Employees (pr_id, emp_id) VALUES
                                                  (1,1),(1,6),(2,2),(2,7),(3,3),(3,8),(4,4),(4,9),
                                                  (5,5),(5,10),(6,6),(6,11),(7,7),(7,12),(8,8),(8,13),
                                                  (9,9),(9,14),(10,10),(10,15),(11,11),(11,16),(12,12),(12,17),
                                                  (13,13),(13,18),(14,14),(14,19),(15,15),(15,20),(16,16),(16,1),
                                                  (17,17),(17,2),(18,18),(18,3),(19,19),(19,4),(20,20),(20,5),
                                                  (21,1),(21,11),(22,2),(22,12),(23,3),(23,13),(24,4),(24,14),
                                                  (25,5),(25,15),(26,6),(26,16),(27,7),(27,17),(28,8),(28,18),
                                                  (29,9),(29,19),(30,10),(30,20),(31,11),(31,1),(32,12),(32,2),
                                                  (33,13),(33,3),(34,14),(34,4),(35,15),(35,5),(36,16),(36,6),
                                                  (37,17),(37,7),(38,18),(38,8),(39,19),(39,9),(40,20),(40,10),
                                                  (41,1),(41,16),(42,2),(42,17),(43,3),(43,18),(44,4),(44,19),
                                                  (45,5),(45,20),(46,6),(46,11),(47,7),(47,12),(48,8),(48,13),
                                                  (49,9),(49,14),(50,10),(50,15);