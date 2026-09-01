CREATE TABLE notifications (
                               id BIGINT NOT NULL AUTO_INCREMENT,
                               user_id BIGINT NOT NULL,
                               message VARCHAR(255) NOT NULL,
                               type VARCHAR(20) NOT NULL,
                               status VARCHAR(20) NOT NULL,
                               PRIMARY KEY (id)
);