-- Inserir um computador de teste
INSERT INTO COMPUTADOR (nome, marca, processador, memoria_ram, armazenamento, preco, descricao, image_url, is_deleted)
VALUES (
    'Notebook Dell Inspiron',
    'Dell',
    'Intel Core i5',
    '8GB',
    '256GB SSD',
    3500.00,
    'Notebook Dell Inspiron com processador Intel Core i5, 8GB de RAM e 256GB de SSD',
    '/images/laptop_moderno.jpg',
    NULL
);

-- Inserir usuário admin inicial (senha: admin)
INSERT INTO usuario (username, password, is_admin, nome, email)
VALUES ('admin', '$2a$10$8K1p/a0dR1xqMZRqF2YQPO3V7k3Q3Q3Q3Q3Q3Q3Q3Q3Q3Q3Q3Q3Q', true, 'Administrador', 'admin@example.com');

-- Inserir usuário comum padrão (senha: user)
INSERT INTO usuario (username, password, is_admin, nome, email)
VALUES ('user', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', false, 'Usuário Padrão', 'user@example.com'); 