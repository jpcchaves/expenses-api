ALTER TABLE expenses
ADD COLUMN notification_active BOOLEAN DEFAULT TRUE NOT NULL;

UPDATE expenses
SET notification_active = TRUE;
