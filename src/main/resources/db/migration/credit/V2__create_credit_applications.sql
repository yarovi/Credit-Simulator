CREATE TABLE credit_applications
(
    id                     UUID PRIMARY KEY,
    customer_id            UUID        NOT NULL,
    offer_id               UUID        NOT NULL,
    destination_account_id UUID        NOT NULL,
    terms_accepted         BOOLEAN     NOT NULL,
    terms_accepted_at      TIMESTAMPTZ,
    status                 VARCHAR(20) NOT NULL,
    created_at             TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_application_offer
        FOREIGN KEY (offer_id)
            REFERENCES credit_offers (id),

    CONSTRAINT chk_application_status
        CHECK (status IN ('CANCELLED', 'DISBURSED')),

    CONSTRAINT chk_terms_acceptance
        CHECK (
            terms_accepted = TRUE
                AND terms_accepted_at IS NOT NULL
            )
);
