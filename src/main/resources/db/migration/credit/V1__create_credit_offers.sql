CREATE TABLE credit_offers
(
    id          UUID PRIMARY KEY,
    offer_code  VARCHAR(16)    NOT NULL UNIQUE,
    customer_id UUID           NOT NULL,
    amount      NUMERIC(18, 2) NOT NULL,
    currency    CHAR(3)        NOT NULL,
    annual_rate NUMERIC(9, 4)  NOT NULL,
    term_months INTEGER        NOT NULL,
    valid_from  TIMESTAMPTZ    NOT NULL,
    valid_until TIMESTAMPTZ    NOT NULL,

    CONSTRAINT chk_offer_code
        CHECK (offer_code ~ '^[0-9A-F]{16}$'
) ,

    CONSTRAINT chk_offer_amount
        CHECK (amount > 0),

    CONSTRAINT chk_offer_rate
        CHECK (annual_rate >= 0),

    CONSTRAINT chk_offer_term
        CHECK (term_months > 0),

    CONSTRAINT chk_offer_validity
        CHECK (valid_until > valid_from)
);

CREATE INDEX idx_credit_offers_customer
    ON credit_offers (customer_id);
