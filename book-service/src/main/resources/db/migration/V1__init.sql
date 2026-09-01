CREATE TABLE books (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       title VARCHAR(150) NOT NULL,
                       author VARCHAR(100) NOT NULL,
                       price DOUBLE NOT NULL,
                       stock INT NOT NULL,
                       PRIMARY KEY (id)
);