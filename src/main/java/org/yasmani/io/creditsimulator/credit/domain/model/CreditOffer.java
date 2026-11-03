package org.yasmani.io.creditsimulator.credit.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CreditOffer(
    UUID id,
    String offerCode,
    UUID customerId,
    BigDecimal amount,
    String currency,
    BigDecimal annualRate,
    Integer termMonths,
    OffsetDateTime validFrom,
    OffsetDateTime validUntil
) {
}
