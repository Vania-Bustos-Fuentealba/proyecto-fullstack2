CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(150) NOT NULL,
                       phone VARCHAR(20) NOT NULL,
                       address VARCHAR(200) NOT NULL,
                       PRIMARY KEY (id),
                       UNIQUE KEY uk_users_email (email)
);