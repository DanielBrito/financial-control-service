CREATE TABLE assignments(
    id SERIAL PRIMARY KEY,
    expense_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    section VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE
);
