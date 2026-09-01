CREATE TABLE orders (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        user_id BIGINT NOT NULL,
                        total DOUBLE NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        PRIMARY KEY (id)
);