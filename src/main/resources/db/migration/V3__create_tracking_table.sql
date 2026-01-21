CREATE TABLE tracking(
    id                UUID          PRIMARY KEY,
    expense_id        UUID          NOT NULL,
    delivery_forecast DATE,
    code              VARCHAR(255),
    shipping_company  VARCHAR(255),
    url               VARCHAR,
    status            VARCHAR(100),
    created_at        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE
);
