CREATE TABLE account_transactions
(
    id               UUID PRIMARY KEY,
    account_id       UUID           NOT NULL,
    disbursement_id  UUID UNIQUE,
    amount           NUMERIC(18, 2) NOT NULL,
    transaction_type VARCHAR(20)    NOT NULL,
    created_at       TIMESTAMPTZ    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transaction_account
        FOREIGN KEY (account_id)
            REFERENCES accounts (id),

    CONSTRAINT fk_transaction_disbursement
        FOREIGN KEY (disbursement_id)
            REFERENCES disbursements (id),

    CONSTRAINT chk_transaction_type
        CHECK (transaction_type IN ('CREDIT', 'DEBIT')),

    CONSTRAINT chk_transaction_amount
        CHECK (amount > 0)
);

CREATE INDEX idx_account_transactions_history
    ON account_transactions (account_id, created_at DESC);
