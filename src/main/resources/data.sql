INSERT INTO tb_endereco (bairro, cep, complemento, localidade, logradouro, numero, uf) VALUES ('Mecejana', '69304-200', 'Ap 5', 'Boa Vista', 'Rua João Antony', '3329', 'RR');
INSERT INTO tb_endereco (bairro, cep, complemento, localidade, logradouro, numero, uf) VALUES ('Milão', '76901-710', 'Casa', 'Ji-Paraná', 'Rua Durval Bartolomeu Trigueiro Mendes', '632', 'RO');
INSERT INTO tb_endereco (bairro, cep, complemento, localidade, logradouro, numero, uf) VALUES ('Centenário', '89283-135', 'Casa', 'São Bento do Sul', 'Rua Euclides Ciriaco Airoso', '1157', 'SC');

INSERT INTO tb_role (authority) VALUES ('ADMIN');
INSERT INTO tb_role (authority) VALUES ('USER');

INSERT INTO tb_usuario (cpf, data_nascimento, email, nome, role_id, password) VALUES ('05219602563', TIMESTAMP WITH TIME ZONE '2000-07-05T00:00:00Z', 'valdomiro@gmail.com', 'Valdomiro das Neves', 2, '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG'); 
INSERT INTO tb_usuario (cpf, data_nascimento, email, nome, role_id, password) VALUES ('05215982563', TIMESTAMP WITH TIME ZONE '1990-09-22T00:00:00Z', 'marinalva@gmail.com', 'Marinalva Pinto', 2, '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_usuario (cpf, data_nascimento, email, nome, role_id, password) VALUES ('05219609800', TIMESTAMP WITH TIME ZONE '1995-11-26T00:00:00Z', 'jeremias@gmail.com', 'Jeremias das Candongas Filho', 1, '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_usuario_endereco (usuario_id, endereco_id) VALUES (1, 1);
INSERT INTO tb_usuario_endereco (usuario_id, endereco_id) VALUES (2, 1);
INSERT INTO tb_usuario_endereco (usuario_id, endereco_id) VALUES (3, 2);
INSERT INTO tb_usuario_endereco (usuario_id, endereco_id) VALUES (3, 3);
