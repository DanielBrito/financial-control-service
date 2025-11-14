CREATE TABLE savings(
    id          UUID      PRIMARY KEY,
    expense_id  UUID      NOT NULL,
    goal        DECIMAL   NOT NULL,
    collected   DECIMAL   NOT NULL DEFAULT 0.0,
    deadline    DATE,
    description VARCHAR,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE
);
