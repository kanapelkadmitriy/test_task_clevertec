CREATE TABLE product
(
    id            BIGSERIAL PRIMARY KEY,
    description   VARCHAR(255),
    price         NUMERIC DEFAULT 0.00,
    quantity      INT4    DEFAULT 0,
    is_discounted BOOLEAN
);

CREATE TABLE card
(
    id            BIGSERIAL PRIMARY KEY,
    number        VARCHAR(255) UNIQUE,
    discount      INT4    DEFAULT 0
);