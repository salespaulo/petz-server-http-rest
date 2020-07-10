-- Role X Privileges
INSERT INTO role_priv(
            role_id, privilege_id)
    SELECT r.id, p.id FROM role r, privilege p
    WHERE r.id = (SELECT id FROM role WHERE name = 'Guest')
    AND p.id = (SELECT id FROM privilege WHERE name = 'PROFILE_GET');

INSERT INTO role_priv(
            role_id, privilege_id)
    SELECT r.id, p.id FROM role r, privilege p
    WHERE r.id = (SELECT id FROM role WHERE name = 'Guest')
    AND p.id = (SELECT id FROM privilege WHERE name = 'PETZ_LIST');

INSERT INTO role_priv(
            role_id, privilege_id)
    SELECT r.id, p.id FROM role r, privilege p
    WHERE r.id = (SELECT id FROM role WHERE name = 'Petz')
    AND p.id = (SELECT id FROM privilege WHERE name = 'PETZ_GET');

INSERT INTO role_priv(
            role_id, privilege_id)
    SELECT r.id, p.id FROM role r, privilege p
    WHERE r.id = (SELECT id FROM role WHERE name = 'Petz')
    AND p.id = (SELECT id FROM privilege WHERE name = 'PETZ_LIST');

INSERT INTO role_priv(
            role_id, privilege_id)
    SELECT r.id, p.id FROM role r, privilege p
    WHERE r.id = (SELECT id FROM role WHERE name = 'Petz')
    AND p.id = (SELECT id FROM privilege WHERE name = 'PETZ_SAVE');

INSERT INTO role_priv(
            role_id, privilege_id)
    SELECT r.id, p.id FROM role r, privilege p
    WHERE r.id = (SELECT id FROM role WHERE name = 'Admin')
    AND p.id = (SELECT id FROM privilege WHERE name = 'PROFILE_GET');

INSERT INTO role_priv(
            role_id, privilege_id)
    SELECT r.id, p.id FROM role r, privilege p
    WHERE r.id = (SELECT id FROM role WHERE name = 'Admin')
    AND p.id = (SELECT id FROM privilege WHERE name = 'PETZ_GET');

INSERT INTO role_priv(
            role_id, privilege_id)
    SELECT r.id, p.id FROM role r, privilege p
    WHERE r.id = (SELECT id FROM role WHERE name = 'Admin')
    AND p.id = (SELECT id FROM privilege WHERE name = 'PETZ_LIST');
    
INSERT INTO role_priv(
            role_id, privilege_id)
    SELECT r.id, p.id FROM role r, privilege p
    WHERE r.id = (SELECT id FROM role WHERE name = 'Admin')
    AND p.id = (SELECT id FROM privilege WHERE name = 'PETZ_SAVE');

INSERT INTO role_priv(
            role_id, privilege_id)
    SELECT r.id, p.id FROM role r, privilege p
    WHERE r.id = (SELECT id FROM role WHERE name = 'Admin')
    AND p.id = (SELECT id FROM privilege WHERE name = 'PETZ_DELETE');

-- Group X Roles
INSERT INTO usergrp_role(
            usergrp_id, role_id)
    SELECT g.id, r.id FROM usergroup g, role r
    WHERE g.id = (SELECT id FROM usergroup WHERE name = 'Root')
    AND r.id = (SELECT id FROM role WHERE name = 'Guest');

INSERT INTO usergrp_role(
            usergrp_id, role_id)
    SELECT g.id, r.id FROM usergroup g, role r
    WHERE g.id = (SELECT id FROM usergroup WHERE name = 'Root')
    AND r.id = (SELECT id FROM role WHERE name = 'Petz');

INSERT INTO usergrp_role(
            usergrp_id, role_id)
    SELECT g.id, r.id FROM usergroup g, role r
    WHERE g.id = (SELECT id FROM usergroup WHERE name = 'Root')
    AND r.id = (SELECT id FROM role WHERE name = 'Admin');

-- User X Groups    
INSERT INTO user_usergrp(
			user_id, 
			usergrp_id) 
	SELECT u.id, g.id FROM users u, usergroup g 
	WHERE u.id = (SELECT id FROM users WHERE username = 'john.admin@ps.org') 
	AND g.id = (SELECT id FROM usergroup WHERE name = 'Root');

-- User X Roles
INSERT INTO user_role (user_id, role_id)
	SELECT u.id, r.id FROM users u, role r WHERE u.username = 'custom.user@ps.org'
	AND r.id = (SELECT id FROM role WHERE name = 'Petz');
    
INSERT INTO user_role (user_id, role_id)
	SELECT u.id, r.id FROM users u, role r WHERE u.username = 'ana.guest@ps.org'
	AND r.id = (SELECT id FROM role WHERE name = 'Guest');

INSERT INTO user_role (user_id, role_id)
	SELECT u.id, r.id FROM users u, role r WHERE u.username = 'mike.operator@ps.org'
	AND r.id = (SELECT id FROM role WHERE name = 'Admin');