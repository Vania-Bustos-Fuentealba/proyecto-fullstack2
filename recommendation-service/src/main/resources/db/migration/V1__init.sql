CREATE TABLE recommendations (
                                 id BIGINT NOT NULL AUTO_INCREMENT,
                                 user_id BIGINT NOT NULL,
                                 book_id BIGINT NOT NULL,
                                 recommendation_text VARCHAR(500) NOT NULL,
                                 PRIMARY KEY (id)
);