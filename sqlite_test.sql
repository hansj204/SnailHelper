DROP TABLE IF EXISTS test_table;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS blob_clob;

CREATE TABLE test_table (
  value1 VARCHAR(255),
  value2 INT NOT NULL,
  value3 FLOAT 
);

INSERT INTO test_table VALUES('Hello1234', 123, 3.14);
INSERT INTO test_table VALUES('Hello5678', 999, 6);

CREATE TABLE student (
  id INT PRIMARY KEY,
  name VARCHAR(20),
  grade INT
);

INSERT INTO student VALUES(1, '김미림', 1);
INSERT INTO student VALUES(2, '박미림', 2);
INSERT INTO student VALUES(3, '서미림', 3);

CREATE TABLE blob_clob (
  pk INT PRIMARY KEY,
  b BLOB,
  c TEXT
);

.schema test_table
.schema student
.schema blob_clob
