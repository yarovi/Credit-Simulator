package org.yasmani.io.creditsimulator.credit.infrasatructure.persistence;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yasmani.io.creditsimulator.credit.domain.port.out.CreditOfferRepositoryPort;
import org.yasmani.io.creditsimulator.support.IntegrationTest;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


//@SpringBootTest
@IntegrationTest
class CreditOfferPersistenceAdapterIntegrationTest {

  @Autowired
  private CreditOfferRepositoryPort repositoryPort;

  private static final OffsetDateTime AT =
      OffsetDateTime.parse("2026-11-02T12:00:00Z");

  @Test
  void shouldFindThreeActiveOffersForAna() {

    UUID customerId = UUID.fromString(
        "10000000-0000-4000-8000-000000000001"
    );

    var offers = repositoryPort.findActiveByCustomerId(
        customerId, AT
    );

    assertEquals(3, offers.size());
  }

  @Test
  void shouldNotReturnExpiredOffersForElena() {

    UUID customerId = UUID.fromString(
        "10000000-0000-4000-8000-000000000005"
    );

    var offers = repositoryPort.findActiveByCustomerId(
        customerId, AT
    );

    assertTrue(offers.isEmpty());
  }

}
