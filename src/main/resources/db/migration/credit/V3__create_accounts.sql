CREATE TABLE accounts
(
    id             UUID PRIMARY KEY,
    customer_id    UUID           NOT NULL,
    account_number VARCHAR(30)    NOT NULL UNIQUE,
    currency       CHAR(3)        NOT NULL,
    balance        NUMERIC(18, 2) NOT NULL,
    status         VARCHAR(10)    NOT NULL,
    version        BIGINT         NOT NULL DEFAULT 0,

    CONSTRAINT chk_account_status
        CHECK (status IN ('ACTIVE', 'INACTIVE'))
);

CREATE INDEX idx_accounts_customer
    ON accounts (customer_id);

ALTER TABLE credit_applications
    ADD CONSTRAINT fk_application_account
        FOREIGN KEY (destination_account_id)
            REFERENCES accounts (id);
