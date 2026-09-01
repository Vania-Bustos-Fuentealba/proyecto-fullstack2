CREATE TABLE reviews (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_id BIGINT NOT NULL,
        book_id BIGINT NOT NULL,
        comment VARCHAR(500) NOT NULL,
        rating INT NOT NULL
);