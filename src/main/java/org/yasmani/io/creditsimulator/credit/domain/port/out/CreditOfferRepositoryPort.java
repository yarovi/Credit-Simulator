package org.yasmani.io.creditsimulator.credit.domain.port.out;

import org.yasmani.io.creditsimulator.credit.domain.model.CreditOffer;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.List;
public interface CreditOfferRepositoryPort {

  List<CreditOffer> findActiveByCustomerId(
      UUID customerId,
      OffsetDateTime at
  );
}