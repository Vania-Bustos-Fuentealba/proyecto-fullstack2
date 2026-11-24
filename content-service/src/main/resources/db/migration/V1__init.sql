CREATE TABLE posts (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    title       VARCHAR(150) NOT NULL,
    summary     VARCHAR(300) NOT NULL,
    image_url   VARCHAR(500),
    image_alt   VARCHAR(150),
    content     TEXT         NOT NULL,
    created_at  DATETIME     NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE contact_messages (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL,
    message     TEXT         NOT NULL,
    is_read     BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at  DATETIME     NOT NULL,
    PRIMARY KEY (id)
);