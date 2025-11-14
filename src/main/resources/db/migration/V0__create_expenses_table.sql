CREATE TABLE expenses(
    id          UUID          PRIMARY KEY,
    priority    VARCHAR(10)   NOT NULL,
    name        VARCHAR       NOT NULL,
    category    VARCHAR(50)   NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    description VARCHAR,
    place       VARCHAR,
    url         VARCHAR,
    comment     VARCHAR,
    grouping    VARCHAR(50),
    created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);
