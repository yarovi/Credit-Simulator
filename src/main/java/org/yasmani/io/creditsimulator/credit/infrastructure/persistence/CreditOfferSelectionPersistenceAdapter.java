package org.yasmani.io.creditsimulator.credit.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import org.yasmani.io.creditsimulator.credit.domain.model.CreditOfferSelection;
import org.yasmani.io.creditsimulator.credit.domain.port.out.CreditOfferSelectionRepositoryPort;

import java.util.UUID;

@Repository
public class CreditOfferSelectionPersistenceAdapter implements CreditOfferSelectionRepositoryPort {

  private final CreditOfferSelectionJpaRepository repository;

  public CreditOfferSelectionPersistenceAdapter(
      CreditOfferSelectionJpaRepository repository
  ) {
    this.repository = repository;
  }

  @Override
  public boolean existsByCustomerId(UUID customerId) {
    return repository.existsByCustomerId(customerId);
  }

  @Override
  public CreditOfferSelection save(CreditOfferSelection selection) {

    var entity = new CreditOfferSelectionEntity(
        selection.id(),
        selection.customerId(),
        selection.offerId(),
        selection.selectedAt(),
        selection.status()
    );

    var saved = repository.saveAndFlush(entity);

    return new CreditOfferSelection(
        saved.getId(),
        saved.getCustomerId(),
        saved.getOfferId(),
        saved.getSelectedAt(),
        saved.getStatus()
    );
  }
}