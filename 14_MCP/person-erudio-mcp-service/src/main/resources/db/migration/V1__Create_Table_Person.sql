CREATE TABLE Person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    nationality VARCHAR(50) NOT NULL,
    gender ENUM('MALE', 'FEMALE') NOT NULL
);
