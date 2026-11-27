-- Instalación limpia: el nombre debe coincidir con @Table(name = "auth_users").
CREATE TABLE auth_users (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(150) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL,
                       PRIMARY KEY (id),
                       UNIQUE KEY uk_auth_users_email (email)
);
