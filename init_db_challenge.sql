CREATE DATABASE db_challenge;
\c db_challenge;

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


CREATE TABLE accounts (
    id_account SERIAL PRIMARY KEY,
    number VARCHAR(20) NOT NULL UNIQUE, 
    type VARCHAR(10) NOT NULL, 

    status BOOLEAN NOT NULL DEFAULT TRUE,
    id_client BIGINT NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (id_client) REFERENCES clients(id_client) ON DELETE CASCADE
);

CREATE TABLE transactions (
    id_transaction SERIAL PRIMARY KEY, 
    date_transaction TIMESTAMP NOT NULL DEFAULT NOW(), 
    amount DOUBLE PRECISION NOT NULL, 
    balance DOUBLE PRECISION NOT NULL, 
    initial_balance DOUBLE PRECISION NOT NULL, 
    id_account BIGINT NOT NULL,  
    CONSTRAINT fk_account FOREIGN KEY (id_account) REFERENCES accounts(id_account) ON DELETE CASCADE
);


