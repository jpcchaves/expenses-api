INSERT INTO users
(id, email, "name", "password", created_at, updated_at)
VALUES(nextval('seq_user'), 'testesadotar@outlook.com', 'Joao Paulo', '12345678', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO users_roles
(user_id, role_id)
VALUES((SELECT id
FROM users
WHERE email='testesadotar@outlook.com'), (SELECT id
FROM roles
WHERE name='ROLE_TEST'));

INSERT INTO expense_sources
(id, created_at, "name", updated_at, user_id)
VALUES(nextval('seq_expense_source'), CURRENT_TIMESTAMP, 'Fonte de Despesa Tal', CURRENT_TIMESTAMP, (SELECT id
FROM users
WHERE email='testesadotar@outlook.com'));

INSERT INTO expenses
(id, created_at, due_date, expense_title, updated_at, expense_source_id, user_id)
VALUES(nextval('seq_expense'), CURRENT_TIMESTAMP, '2024-08-26', 'Zona Azul', CURRENT_TIMESTAMP, (select id from expense_sources where name = 'Fonte de Despesa Tal' and user_id = (SELECT id
FROM users
WHERE email='testesadotar@outlook.com')), (select id from users where email = 'testesadotar@outlook.com'));

INSERT INTO expenses
(id, created_at, due_date, expense_title, updated_at, expense_source_id, user_id)
VALUES(nextval('seq_expense'), CURRENT_TIMESTAMP, '2024-08-26', 'Fatura do Cartão', CURRENT_TIMESTAMP, (select id from expense_sources where name = 'Fonte de Despesa Tal' and user_id = (SELECT id
FROM users
WHERE email='testesadotar@outlook.com')), (select id from users where email = 'testesadotar@outlook.com'));

INSERT INTO expenses
(id, created_at, due_date, expense_title, updated_at, expense_source_id, user_id)
VALUES(nextval('seq_expense'), CURRENT_TIMESTAMP, '2024-08-26', 'Conta de Água', CURRENT_TIMESTAMP, (select id from expense_sources where name = 'Fonte de Despesa Tal' and user_id = (SELECT id
FROM users
WHERE email='testesadotar@outlook.com')), (select id from users where email = 'testesadotar@outlook.com'));

INSERT INTO expenses
(id, created_at, due_date, expense_title, updated_at, expense_source_id, user_id)
VALUES(nextval('seq_expense'), CURRENT_TIMESTAMP, '2024-08-26', 'Conta de Luz', CURRENT_TIMESTAMP, (select id from expense_sources where name = 'Fonte de Despesa Tal' and user_id = (SELECT id
FROM users
WHERE email='testesadotar@outlook.com')), (select id from users where email = 'testesadotar@outlook.com'));

INSERT INTO expenses
(id, created_at, due_date, expense_title, updated_at, expense_source_id, user_id)
VALUES(nextval('seq_expense'), CURRENT_TIMESTAMP, '2024-08-26', 'Seguro da Moto', CURRENT_TIMESTAMP, (select id from expense_sources where name = 'Fonte de Despesa Tal' and user_id = (SELECT id
FROM users
WHERE email='testesadotar@outlook.com')), (select id from users where email = 'testesadotar@outlook.com'));