CREATE database PhoneBook;
USE PhoneBook;

CREATE TABLE employees(
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  first_name CHAR(50) NOT NULL,
  last_name CHAR(50) NOT NULL,
  department_id MEDIUMINT ,
  project CHAR(30) NOT NULL,
  email CHAR(30) NOT NULL UNIQUE,
  PRIMARY KEY (id),
  UNIQUE (first_name, last_name)
);

CREATE TABLE phones(
  id MEDIUMINT NOT NULL AUTO_INCREMENT,
  country_code CHAR(5) NOT NULL,
  city_code CHAR(5) NOT NULL,
  phone_number CHAR(10) NOT NULL,
  is_mobile TINYINT(1) NOT NULL,
  employee_id MEDIUMINT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

CREATE TABLE departments(
  id MEDIUMINT NOT NULL AUTO_INCREMENT ,
  name char(50) NOT NULL UNIQUE,
  manager mediumint NOT NULL,
  primary key (id)
);
ALTER TABLE departments ADD FOREIGN KEY (manager) REFERENCES employees(id) ON DELETE CASCADE;
ALTER TABLE employees ADD FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL;
ALTER TABLE phones ADD FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE;