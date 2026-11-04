package org.yasmani.io.creditsimulator.credit.domain.port.out;

import org.yasmani.io.creditsimulator.credit.domain.model.CreditOfferSelection;

import java.util.UUID;

public interface CreditOfferSelectionRepositoryPort {
  boolean existsByCustomerId(UUID customerId);

  CreditOfferSelection save(CreditOfferSelection selection);
}
