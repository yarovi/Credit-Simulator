package org.yasmani.io.creditsimulator.credit.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import org.yasmani.io.creditsimulator.credit.domain.model.CreditOffer;
import org.yasmani.io.creditsimulator.credit.domain.port.out.CreditOfferRepositoryPort;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.List;
@Repository
public class CreditOfferPersistenceAdapter
    implements CreditOfferRepositoryPort {

  private final CreditOfferJpaRepository repository;

  public CreditOfferPersistenceAdapter(
      CreditOfferJpaRepository repository
  ) {
    this.repository = repository;
  }

  @Override
  public List<CreditOffer> findActiveByCustomerId(
      UUID customerId,
      OffsetDateTime at
  ) {
    return repository
        .findByCustomerIdAndValidFromLessThanEqualAndValidUntilGreaterThanEqual(
            customerId, at, at
        )
        .stream()
        .map(this::toDomain)
        .toList();
  }

  private CreditOffer toDomain(CreditOfferEntity entity) {
    return new CreditOffer(
        entity.getId(),
        entity.getOfferCode(),
        entity.getCustomerId(),
        entity.getAmount(),
        entity.getCurrency(),
        entity.getAnnualRate(),
        entity.getTermMonths(),
        entity.getValidFrom(),
        entity.getValidUntil()
    );
  }
}
