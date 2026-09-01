CREATE TABLE inventory (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           book_id BIGINT NOT NULL,
                           stock INT NOT NULL,
                           PRIMARY KEY (id)
);