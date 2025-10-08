CREATE TABLE balances(
    id SERIAL NOT NULL,
    source VARCHAR(50) NOT NULL,
    amount DECIMAL NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
