CREATE TABLE savings(
    id SERIAL NOT NULL,
    expense_id INTEGER NOT NULL,
    goal DECIMAL NOT NULL,
    collected DECIMAL NOT NULL DEFAULT 0.0,
    deadline DATE,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE
);
