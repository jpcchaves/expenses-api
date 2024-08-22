alter table if exists expenses
       add column notification_frequency varchar(50) check (notification_frequency in ('DAILY','WEEKLY','MONTHLY','NONE'));

UPDATE expenses
SET notification_frequency = 'DAILY'
WHERE notification_frequency IS NULL;

ALTER TABLE expenses
ALTER COLUMN notification_frequency SET NOT NULL;

ALTER TABLE expenses
ALTER COLUMN notification_frequency DROP DEFAULT;
