CREATE TABLE expenses(
    id SERIAL PRIMARY KEY,
    priority INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL NOT NULL,
    description VARCHAR(255),
    place VARCHAR(50),
    url VARCHAR,
    comment VARCHAR,
    "group" VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
