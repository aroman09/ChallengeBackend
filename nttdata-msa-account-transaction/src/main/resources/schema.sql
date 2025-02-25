DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accounts;
-- Crear la tabla 'cuentas'
CREATE TABLE accounts (-- Clave primaria auto-incremental
    id_account SERIAL PRIMARY KEY,
    number VARCHAR(20) NOT NULL UNIQUE, -- Número de la cuenta
    type VARCHAR(10) NOT NULL, -- Tipo de cuenta (e.g., ahorro, corriente)
    initial_balance DOUBLE PRECISION NOT NULL, -- Saldo inicial
    status BOOLEAN NOT NULL DEFAULT TRUE,
    id_client BIGINT NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (id_client) REFERENCES clients(id_client) ON DELETE CASCADE
);
-- Crear la tabla 'movimientos'
CREATE TABLE transactions (
    id_transaction SERIAL PRIMARY KEY, -- Clave primaria auto-incremental
    date_transaction TIMESTAMP NOT NULL DEFAULT NOW(), -- Fecha de la transacción
    type VARCHAR(10) NOT NULL, -- Tipo de movimiento (e.g., depósito, retiro)
    amount DOUBLE PRECISION NOT NULL, -- Monto de la transacción
    balance DOUBLE PRECISION NOT NULL, -- Saldo después de la transacción
    initial_balance DOUBLE PRECISION NOT NULL, -- Saldo después de la transacción
    id_account BIGINT NOT NULL,  -- Debe coincidir con `number` de `accounts`
    CONSTRAINT fk_account FOREIGN KEY (id_account) REFERENCES accounts(id_account) ON DELETE CASCADE
);
