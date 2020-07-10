-- User
INSERT INTO users(name, username, password)     --Password: Test
    VALUES ('John Admin', 'john.admin@ps.org', '$2a$10$.z6m5mLsgrHOUrPR1hqsouYOGGDIawvKNLU5LhWUYFyOAfzTlSE4m');

INSERT INTO users(name, username, password)     --Password: Test
    VALUES ('Ana Guest', 'ana.guest@ps.org', '$2a$10$.z6m5mLsgrHOUrPR1hqsouYOGGDIawvKNLU5LhWUYFyOAfzTlSE4m');

INSERT INTO users(name, username, password)     --Password: Test
    VALUES ('Mike Operator', 'mike.operator@ps.org', '$2a$10$.z6m5mLsgrHOUrPR1hqsouYOGGDIawvKNLU5LhWUYFyOAfzTlSE4m');

INSERT INTO users(name, username, password)     --Password: Test
    VALUES ('Custom User', 'custom.user@ps.org', '$2a$10$.z6m5mLsgrHOUrPR1hqsouYOGGDIawvKNLU5LhWUYFyOAfzTlSE4m');

-- User Group
INSERT INTO usergroup(name) VALUES ('Root');

INSERT INTO usergroup(name) VALUES ('Operator');
    
-- Roles
INSERT INTO role(name) VALUES ('Guest');

INSERT INTO role(name) VALUES ('Petz');

INSERT INTO role(name) VALUES ('Admin');

-- Privileges
INSERT INTO privilege(name) VALUES ('PROFILE_GET');

INSERT INTO privilege(name) VALUES ('PETZ_SAVE');

INSERT INTO privilege(name) VALUES ('PETZ_LIST');

INSERT INTO privilege(name) VALUES ('PETZ_GET');

INSERT INTO privilege(name) VALUES ('PETZ_DELETE');