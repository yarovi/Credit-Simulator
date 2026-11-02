package org.yasmani.io.creditsimulator.customer.domain.model;

import java.util.UUID;

public record Customer(
    UUID id,
    String documentNumber,
    String fullName,
    String email,
    String phone,
    String riskStatus
) {
}
