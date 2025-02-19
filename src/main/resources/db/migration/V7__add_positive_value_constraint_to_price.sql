ALTER TABLE expenses
ADD CONSTRAINT check_positive_number CHECK (price > 0.0);
