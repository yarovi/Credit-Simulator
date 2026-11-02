CREATE TABLE customers
(
    id              UUID PRIMARY KEY,
    document_number VARCHAR(8)   NOT NULL UNIQUE,
    full_name       VARCHAR(150) NOT NULL,
    email           VARCHAR(150) NOT NULL,
    phone           VARCHAR(20)  NOT NULL,
    risk_status     CHAR(1)      NOT NULL,
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_customer_risk
        CHECK (risk_status IN ('R', 'N')),

    CONSTRAINT chk_customer_document
        CHECK (document_number ~ '^[0-9]{8}$'
)
    );
