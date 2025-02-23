DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS transactions;

-- Crear la tabla 'cuentas'
CREATE TABLE accounts (
    idAccount SERIAL PRIMARY KEY, -- Clave primaria auto-incremental
    number VARCHAR(20) NOT NULL UNIQUE, -- Número de la cuenta
    type VARCHAR(10) NOT NULL, -- Tipo de cuenta (e.g., ahorro, corriente)
    initialBalance DOUBLE PRECISION NOT NULL, -- Saldo inicial
    status BOOLEAN NOT NULL DEFAULT TRUE -- Estado de la cuenta (activa o inactiva)
    idClient BIGINT REFERENCES clients(idClient) ON DELETE CASCADE
);

-- Crear la tabla 'movimientos'
CREATE TABLE transactions (
    idTransaction SERIAL PRIMARY KEY, -- Clave primaria auto-incremental
    dateTransaction TIMESTAMP NOT NULL DEFAULT NOW(), -- Fecha de la transacción
    type VARCHAR(10) NOT NULL, -- Tipo de movimiento (e.g., depósito, retiro)
    mount DOUBLE PRECISION NOT NULL, -- Monto de la transacción
    balance DOUBLE PRECISION NOT NULL, -- Saldo después de la transacción
    account REFERENCES accounts (number) ON DELETE CASCADE
);
