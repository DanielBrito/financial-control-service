-- HIGHEST (1), HIGH (2), MEDIUM (3), LOW (4)

ALTER TABLE expenses
ADD CONSTRAINT check_number_range CHECK (priority BETWEEN 1 AND 4);
