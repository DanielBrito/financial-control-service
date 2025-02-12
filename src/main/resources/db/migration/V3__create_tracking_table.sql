CREATE TABLE tracking(
    id SERIAL NOT NULL,
    expense_id INTEGER NOT NULL,
    delivery_forecast DATE,
    code VARCHAR(255),
    shipping_company VARCHAR(255),
    url VARCHAR,
    status VARCHAR(100),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE
);
