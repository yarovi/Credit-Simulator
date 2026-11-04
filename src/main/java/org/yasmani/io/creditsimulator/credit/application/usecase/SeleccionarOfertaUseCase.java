package org.yasmani.io.creditsimulator.credit.application.usecase;


import org.springframework.stereotype.Service;
import org.yasmani.io.creditsimulator.credit.domain.model.CreditOfferSelection;
import org.yasmani.io.creditsimulator.credit.domain.port.out.CreditOfferRepositoryPort;
import org.yasmani.io.creditsimulator.credit.domain.port.out.CreditOfferSelectionRepositoryPort;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class SeleccionarOfertaUseCase {

  private final CreditOfferRepositoryPort offers;
  private final CreditOfferSelectionRepositoryPort selections;

  public SeleccionarOfertaUseCase(
      CreditOfferRepositoryPort offers,
      CreditOfferSelectionRepositoryPort selections
  ) {
    this.offers = offers;
    this.selections = selections;
  }

  public CreditOfferSelection execute(
      UUID customerId,
      String offerCode
  ) {
    if (selections.existsByCustomerId(customerId)) {
      throw new OfertaYaProcesadaException();
    }

    OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

    var offer = offers.findActiveByCustomerId(customerId, now)
        .stream()
        .filter(item -> item.offerCode().equals(offerCode))
        .findFirst()
        .orElseThrow(OfertaNoDisponibleException::new);

    return selections.save(
        new CreditOfferSelection(
            UUID.randomUUID(),
            customerId,
            offer.id(),
            now,
            "SELECTED"
        )
    );
  }

  public static class OfertaYaProcesadaException
      extends RuntimeException {
  }

  public static class OfertaNoDisponibleException
      extends RuntimeException {
  }

}
