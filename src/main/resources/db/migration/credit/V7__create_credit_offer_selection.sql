CREATE TABLE credit_offer_selection
(
    id          UUID PRIMARY KEY,
    customer_id UUID        NOT NULL,
    offer_id    UUID        NOT NULL,
    selected_at TIMESTAMPTZ NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT 'SELECTED',

    CONSTRAINT ck_credit_offer_selection_status
        CHECK (status IN ('SELECTED', 'DISBURSED')),

    CONSTRAINT uk_credit_offer_selection_customer
        UNIQUE (customer_id),

    CONSTRAINT fk_credit_offer_selection_offer
        FOREIGN KEY (offer_id)
            REFERENCES credit_offers (id)
);