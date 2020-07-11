-- User
INSERT INTO users(name, username, password)     --Password: Test
    VALUES ('John Admin', 'john.admin@ps.org', '$2a$10$VPZ40i6ON99Jf967Xqd1Ke7FRf7Tawe6zj5VeTuoMD0ZuTWzn61/e');

INSERT INTO users(name, username, password)     --Password: Test
    VALUES ('Ana Guest', 'ana.guest@ps.org', '$2a$10$VPZ40i6ON99Jf967Xqd1Ke7FRf7Tawe6zj5VeTuoMD0ZuTWzn61/e');

INSERT INTO users(name, username, password)     --Password: Test
    VALUES ('Mike Operator', 'mike.operator@ps.org', '$2a$10$VPZ40i6ON99Jf967Xqd1Ke7FRf7Tawe6zj5VeTuoMD0ZuTWzn61/e');

INSERT INTO users(name, username, password)     --Password: Test
    VALUES ('Custom User', 'custom.user@ps.org', '$2a$10$VPZ40i6ON99Jf967Xqd1Ke7FRf7Tawe6zj5VeTuoMD0ZuTWzn61/e');

-- User Group
INSERT INTO usergroup(name) VALUES ('Root');

INSERT INTO usergroup(name) VALUES ('Operator');
    
-- Roles
INSERT INTO role(name) VALUES ('Guest');

INSERT INTO role(name) VALUES ('Petz');

INSERT INTO role(name) VALUES ('Admin');

-- Privileges
INSERT INTO privilege(name) VALUES ('PROFILE_GET');

INSERT INTO privilege(name) VALUES ('USER_DELETE');

INSERT INTO privilege(name) VALUES ('USER_SAVE');

INSERT INTO privilege(name) VALUES ('USER_LIST');

INSERT INTO privilege(name) VALUES ('USER_GET');

INSERT INTO privilege(name) VALUES ('CLIENTE_DELETE');

INSERT INTO privilege(name) VALUES ('CLIENTE_SAVE');

INSERT INTO privilege(name) VALUES ('CLIENTE_LIST');

INSERT INTO privilege(name) VALUES ('CLIENTE_GET');

INSERT INTO privilege(name) VALUES ('PET_SAVE');

INSERT INTO privilege(name) VALUES ('PET_LIST');

INSERT INTO privilege(name) VALUES ('PET_GET');

INSERT INTO privilege(name) VALUES ('PET_DELETE');

-- Cliente
INSERT INTO cliente(cpf, logradouro, cep, user_id) 
	SELECT '669.482.770-98', 'Rua das Flores, 200', '11111-111', u.id FROM users u WHERE u.username = 'john.admin@ps.org';
	
INSERT INTO cliente(cpf, logradouro, cep, user_id) 
	SELECT '340.587.170-09', 'Avenida dos Estados, 1050', '22222-222', u.id FROM users u WHERE u.username = 'ana.guest@ps.org';
	
INSERT INTO cliente(cpf, logradouro, cep, user_id) 
	SELECT '224.678.370-41', 'Rua Bela Cintra, 544', '33333-333', u.id FROM users u WHERE u.username = 'mike.operator@ps.org';

INSERT INTO cliente(cpf, logradouro, cep, user_id) 
	SELECT '082.584.760-50', 'Estrada da Palestina, 500 - Juquitiba', '44444-444', u.id FROM users u WHERE u.username = 'custom.user@ps.org';

-- Pet
INSERT INTO pet(nome, especie, genero, cliente_id) 
	SELECT 'Huxley', 'Cao', 'Macho', c.id FROM cliente c WHERE c.cpf = '669.482.770-98';

INSERT INTO pet(nome, especie, genero, cliente_id) 
	SELECT 'Zuzi', 'Gato', 'Femea', c.id FROM cliente c WHERE c.cpf = '669.482.770-98';
