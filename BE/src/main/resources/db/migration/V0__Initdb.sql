CREATE DATABASE IF NOT EXISTS PBL6;

CREATE TABLE user
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_by    VARCHAR(255)          NULL,
    created_date  datetime              NULL,
    modified_date datetime              NULL,
    modified_by   VARCHAR(255)          NULL,
    username      VARCHAR(255)          NOT NULL,
    email         VARCHAR(255)          NOT NULL,
    password      VARCHAR(255)          NOT NULL,
    `role`        VARCHAR(255)          NOT NULL,
    full_name     VARCHAR(255)          NULL,
    phone_number  VARCHAR(255)          NULL,
    address       VARCHAR(255)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE music
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    title        VARCHAR(255)       NOT NULL,
    composer_id  BIGINT             NULL,  -- Changed INT to BIGINT
    demo_url     VARCHAR(255)       NULL,
    full_url     VARCHAR(255)       NULL,
    price        DECIMAL            NULL,
    is_approved  BIT(1)             NOT NULL,
    is_purchased BIT(1)             NOT NULL,
    category_id  BIGINT             NULL,  -- Changed INT to BIGINT
    image_url    VARCHAR(255)       NULL,
    CONSTRAINT pk_music PRIMARY KEY (id)
);

CREATE TABLE category
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)       NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE purchase
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_id       BIGINT             NOT NULL,  -- Changed INT to BIGINT
    music_id      BIGINT             NOT NULL,  -- Changed INT to BIGINT
    purchase_date datetime           NOT NULL,
    amount        DECIMAL            NOT NULL,
    CONSTRAINT pk_purchase PRIMARY KEY (id)
);

ALTER TABLE purchase
    ADD CONSTRAINT FK_PURCHASE_ON_MUSIC FOREIGN KEY (music_id) REFERENCES music (id);

ALTER TABLE purchase
    ADD CONSTRAINT FK_PURCHASE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE music
    ADD CONSTRAINT FK_MUSIC_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE music
    ADD CONSTRAINT FK_MUSIC_ON_COMPOSER FOREIGN KEY (composer_id) REFERENCES user (id);
CREATE TABLE wallets
(
    wallet_id  BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT                NOT NULL,
    balance    DECIMAL                NOT NULL,
    updated_at datetime              NOT NULL,
    CONSTRAINT pk_wallets PRIMARY KEY (wallet_id)
);

ALTER TABLE wallets
    ADD CONSTRAINT uc_wallets_user UNIQUE (user_id);

ALTER TABLE wallets
    ADD CONSTRAINT FK_WALLETS_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
CREATE TABLE transaction_history
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    wallet_id       VARCHAR(255)          NULL,
    amount          DECIMAL               NULL,
    transaction_ref VARCHAR(255)          NULL,
    status          VARCHAR(255)          NULL,
    created_at      datetime              NULL,
    CONSTRAINT pk_transactionhistory PRIMARY KEY (id)
);