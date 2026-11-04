package org.yasmani.io.creditsimulator.credit.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditOfferSelectionJpaRepository extends JpaRepository<CreditOfferSelectionEntity, UUID> {

  boolean existsByCustomerId(UUID customerId);
}
