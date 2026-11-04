package org.yasmani.io.creditsimulator.credit.domain.model;


import java.time.OffsetDateTime;
import java.util.UUID;

public record CreditOfferSelection(
    UUID id,
    UUID customerId,
    UUID offerId,
    OffsetDateTime selectedAt,
    String status
) {
}
