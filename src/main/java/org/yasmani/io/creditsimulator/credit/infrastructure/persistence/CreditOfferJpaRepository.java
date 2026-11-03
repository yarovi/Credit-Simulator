package org.yasmani.io.creditsimulator.credit.infrastructure.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface CreditOfferJpaRepository
    extends JpaRepository<CreditOfferEntity, UUID> {

  List<CreditOfferEntity>
  findByCustomerIdAndValidFromLessThanEqualAndValidUntilGreaterThanEqual(
      UUID customerId,
      OffsetDateTime validFrom,
      OffsetDateTime validUntil
  );
}