-- It is one time setup in local PostGres
DROP DATABASE IF EXISTS demodb;
DROP USER IF EXISTS demouser;

CREATE DATABASE demodb;
CREATE USER demouser WITH PASSWORD 'demo@123';
GRANT ALL PRIVILEGES ON DATABASE "demodb" to demouser;

CREATE TABLE login (
  username        TEXT          NOT NULL,
  password        TEXT          NOT NULL,
  CONSTRAINT login_pkey PRIMARY KEY (username)
);

INSERT INTO login VALUES('success','success@123');

CREATE TABLE student (
  reg_no      BIGSERIAL     NOT NULL,
  name        TEXT          NOT NULL,
  age         BIGINT        NOT NULL,
  total_mark  BIGINT        NOT NULL,
  CONSTRAINT student_pkey PRIMARY KEY (reg_no)
);

CREATE TABLE employee (
  emp_no      BIGSERIAL     NOT NULL,
  name        TEXT          NOT NULL,
  age         BIGINT        NOT NULL,
  salary      DECIMAL         NOT NULL  CONSTRAINT positive_salary_amount CHECK (salary > 0),
  CONSTRAINT employee_pkey PRIMARY KEY (emp_no)
);