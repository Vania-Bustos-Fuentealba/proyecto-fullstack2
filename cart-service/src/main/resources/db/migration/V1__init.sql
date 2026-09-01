CREATE TABLE cart (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      user_id BIGINT NOT NULL,
                      book_id BIGINT NOT NULL,
                      quantity INT NOT NULL,
                      PRIMARY KEY (id)
);