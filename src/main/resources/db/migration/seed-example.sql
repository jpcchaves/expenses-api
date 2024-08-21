INSERT INTO users
(id, email, "name", "password", created_at, updated_at)
VALUES(nextval('seq_user'), 'test@test.com', 'Joao Paulo', '12345678', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO roles
(id, "name")
VALUES(nextval('seq_role'), 'ROLE_TEST');

INSERT INTO users_roles
(user_id, role_id)
VALUES((SELECT id
FROM users
WHERE email='test@test.com'), (SELECT id
FROM roles
WHERE name='ROLE_TEST'));

INSERT INTO expense_sources
(id, created_at, "name", updated_at)
VALUES(nextval('seq_expense_source'), CURRENT_TIMESTAMP, 'Assai', CURRENT_TIMESTAMP);

INSERT INTO expenses
(id, amount, created_at, due_date, expense_title, updated_at, expense_source_id, user_id)
VALUES(nextval('seq_expense'), 240, CURRENT_TIMESTAMP, '2024-08-31', 'Despesa com a feira do mes de agosto', CURRENT_TIMESTAMP, (select id from expense_sources where name = 'Assai Atacadista'), (select id from users where email = 'test@test.com'));