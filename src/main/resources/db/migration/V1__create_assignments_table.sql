CREATE TABLE assignments(
    id         UUID         PRIMARY KEY,
    expense_id UUID         NOT NULL,
    quantity   INTEGER      NOT NULL DEFAULT 1,
    section    VARCHAR(50),
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (expense_id) REFERENCES expenses(id) ON DELETE CASCADE
);
