CREATE TABLE disbursements
(
    id             UUID PRIMARY KEY,
    application_id UUID           NOT NULL UNIQUE,
    account_id     UUID           NOT NULL,
    amount         NUMERIC(18, 2) NOT NULL,
    created_at     TIMESTAMPTZ    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_disbursement_application
        FOREIGN KEY (application_id)
            REFERENCES credit_applications (id),

    CONSTRAINT fk_disbursement_account
        FOREIGN KEY (account_id)
            REFERENCES accounts (id),

    CONSTRAINT chk_disbursement_amount
        CHECK (amount > 0)
);
