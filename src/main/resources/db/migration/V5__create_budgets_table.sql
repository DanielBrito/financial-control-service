CREATE TABLE budgets(
    id          UUID          PRIMARY KEY,
    expense_id  UUID          NOT NULL,
    place       VARCHAR(255)  NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    url         VARCHAR,
    description VARCHAR(255),
    preference  INTEGER,
    created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE
);
