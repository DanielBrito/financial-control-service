CREATE TABLE budgets(
    id SERIAL NOT NULL,
    expense_id INTEGER NOT NULL,
    place VARCHAR(255) NOT NULL,
    price DECIMAL NOT NULL,
    url VARCHAR,
    description VARCHAR(255),
    preference INTEGER,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE
);
