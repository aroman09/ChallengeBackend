
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS persons;

CREATE TABLE persons (
    idPerson SERIAL PRIMARY KEY,
    name VARCHAR(100),
    gender VARCHAR(10),
    age INT,
    identification VARCHAR(50),
    direction VARCHAR(255),
    telephone VARCHAR(20)
);

CREATE TABLE clients (
    idClient SERIAL PRIMARY KEY,
    password VARCHAR(255),
    state BOOLEAN NOT NULL DEFAULT TRUE,
    idPerson BIGINT REFERENCES persons(idPerson) ON DELETE CASCADE
);