package org.yasmani.io.creditsimulator.credit.application.usecase;

import org.junit.jupiter.api.Test;
import org.yasmani.io.creditsimulator.credit.domain.model.CreditOffer;
import org.yasmani.io.creditsimulator.credit.domain.port.out.CreditOfferRepositoryPort;
import org.yasmani.io.creditsimulator.customer.domain.model.Customer;
import org.yasmani.io.creditsimulator.customer.domain.port.CustomerRepositoryPort;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;
public class ConsultarElegibilidadUseCaseTest {

  private final CustomerRepositoryPort customers =
      mock(CustomerRepositoryPort.class);

  private final CreditOfferRepositoryPort offers =
      mock(CreditOfferRepositoryPort.class);

  private final ConsultarElegibilidadUseCase useCase =
      new ConsultarElegibilidadUseCase(customers, offers);

  @Test
  void shouldIdentifyCustomerWithActiveOffers() {

    UUID customerId = UUID.fromString(
        "10000000-0000-4000-8000-000000000001"
    );

    var customer = new Customer(
        customerId, "70000001", "Ana Torres",
        null, null, "N"
    );

    when(customers.findByDocumentNumber("70000001"))
        .thenReturn(Optional.of(customer));

    when(offers.findActiveByCustomerId(eq(customerId), any()))
        .thenReturn(List.of(
            mock(CreditOffer.class),
            mock(CreditOffer.class),
            mock(CreditOffer.class)
        ));

    var result = useCase.execute("70000001");

    assertTrue(result.isPresent());
    assertTrue(result.get().hasActiveOffers());
    assertEquals(3, result.get().activeOfferCount());
  }

  @Test
  void shouldReturnEmptyForUnknownCustomer() {

    when(customers.findByDocumentNumber("99999999"))
        .thenReturn(Optional.empty());

    var result = useCase.execute("99999999");

    assertTrue(result.isEmpty());
    verifyNoInteractions(offers);
  }
}
