package org.yasmani.io.creditsimulator.credit.domain.model;

import java.util.UUID;

public record EligibilityResult(
    UUID customerId,
    boolean hasActiveOffers,
    int activeOfferCount
) {
}