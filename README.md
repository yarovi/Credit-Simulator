# Verify Client
podman exec -it credit-customer-db \
psql -U "$CUSTOMER_DB_USER" -d customer_db

# Verify Credit
podman exec -it credit-operation-db \
psql -U "$CREDIT_DB_USER" -d credit_db

# If you stay aside container
SELECT * FROM flyway_schema_history ORDER BY installed_rank;

SELECT COUNT(*) FROM credit_offers;

SELECT COUNT(*) FROM accounts;
