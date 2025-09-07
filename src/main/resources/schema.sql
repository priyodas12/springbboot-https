DROP SEQUENCE IF EXISTS customer_roles_seq;
DROP SEQUENCE IF EXISTS customers_seq;
DROP SEQUENCE IF EXISTS roles_seq;

drop table if exists customer_usernames;
drop table if exists customer_roles;
drop table if exists customers;
drop table if exists roles;

CREATE SEQUENCE IF NOT EXISTS customers_seq START 1;
CREATE SEQUENCE IF NOT EXISTS roles_seq START 1;
CREATE SEQUENCE IF NOT EXISTS customer_roles_seq START 1;

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE TABLE IF NOT EXISTS customers
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    address    VARCHAR(255),
    password   VARCHAR(255) NOT NULL,
    email_id   VARCHAR(100) NOT NULL UNIQUE,
    is_active  BOOLEAN   DEFAULT TRUE,
    phone      VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customer_usernames
(
    username    VARCHAR(50) NOT NULL UNIQUE PRIMARY KEY,
    customer_id BIGSERIAL   NOT NULL REFERENCES customers (id),
    is_active   BOOLEAN   DEFAULT FALSE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS customer_roles
(
    user_id BIGINT NOT NULL REFERENCES customers (id),
    role_id BIGINT NOT NULL REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

CREATE UNIQUE INDEX idx_customer_username ON customers (lower(username));
CREATE INDEX idx_customer_email ON customers (lower(email_id));

-- run outside a transaction for large tables
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_customers_username_trgm
    ON customers USING gin (username gin_trgm_ops);

CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_customers_email_trgm
    ON customers USING gin (email_id gin_trgm_ops);