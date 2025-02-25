DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS persons;

CREATE TABLE persons (
    id_person SERIAL PRIMARY KEY,
    name VARCHAR(100),
    gender VARCHAR(10),
    age INT,
    identification VARCHAR(50),
    direction VARCHAR(255),
    telephone VARCHAR(20)
);

CREATE TABLE clients (
    id_client SERIAL PRIMARY KEY,
    password VARCHAR(30),
    state BOOLEAN NOT NULL DEFAULT TRUE,
    id_person BIGINT REFERENCES persons(id_person) ON DELETE CASCADE
);