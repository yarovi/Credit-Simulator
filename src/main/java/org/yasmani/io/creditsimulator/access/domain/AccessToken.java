package org.yasmani.io.creditsimulator.access.domain;

import java.time.Instant;
import java.util.UUID;

public record AccessToken(
    String value,
    UUID customerId,
    Instant expiresAt
) {
}