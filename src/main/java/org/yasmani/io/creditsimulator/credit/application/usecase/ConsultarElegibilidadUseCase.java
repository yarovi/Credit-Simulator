package org.yasmani.io.creditsimulator.credit.application.usecase;

import org.springframework.stereotype.Service;
import org.yasmani.io.creditsimulator.credit.domain.model.EligibilityResult;
import org.yasmani.io.creditsimulator.credit.domain.port.out.CreditOfferRepositoryPort;
import org.yasmani.io.creditsimulator.customer.domain.port.CustomerRepositoryPort;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class ConsultarElegibilidadUseCase {

  private final CustomerRepositoryPort customerRepositoryPort;
  private final CreditOfferRepositoryPort creditOfferRepositoryPort;

  public ConsultarElegibilidadUseCase(
      CustomerRepositoryPort customerRepositoryPort,
      CreditOfferRepositoryPort creditOfferRepositoryPort
  ) {
    this.customerRepositoryPort = customerRepositoryPort;
    this.creditOfferRepositoryPort = creditOfferRepositoryPort;
  }

  public Optional<EligibilityResult> execute(String documentNumber) {

    return customerRepositoryPort.findByDocumentNumber(documentNumber)
        .map(customer -> {

          var now = OffsetDateTime.now(Clock.systemUTC());

          var offers = creditOfferRepositoryPort
              .findActiveByCustomerId(customer.id(), now);

          return new EligibilityResult(
              customer.id(),
              !offers.isEmpty(),
              offers.size()
          );
        });
  }
}
