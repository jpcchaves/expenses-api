ALTER TABLE expense_sources
ADD COLUMN user_id BIGINT;

UPDATE expense_sources
SET user_id = 1;

ALTER TABLE expense_sources
ALTER COLUMN user_id SET NOT NULL;
