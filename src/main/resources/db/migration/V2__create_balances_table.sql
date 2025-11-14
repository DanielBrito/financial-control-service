CREATE TABLE balances(
    id         UUID          PRIMARY KEY,
    source     VARCHAR(50)   NOT NULL,
    amount     DECIMAL       NOT NULL,
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);
