CREATE TABLE payments (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          order_id BIGINT NOT NULL,
                          amount DOUBLE NOT NULL,
                          payment_method VARCHAR(50) NOT NULL,
                          status VARCHAR(50) NOT NULL,
                          PRIMARY KEY (id)
);