
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS personas;

CREATE TABLE personas (
    personId SERIAL PRIMARY KEY,
    name VARCHAR(100),
    gender VARCHAR(10),
    age INT,
    identification VARCHAR(50),
    direction VARCHAR(255),
    telephone VARCHAR(20)
);

CREATE TABLE clientes (
    clientId SERIAL PRIMARY KEY,
    password VARCHAR(255),
    state BOOLEAN NOT NULL DEFAULT TRUE,
    personId BIGINT REFERENCES personas(personId) ON DELETE CASCADE
);