package org.yasmani.io.creditsimulator.credit.access.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yasmani.io.creditsimulator.credit.application.usecase.SeleccionarOfertaUseCase;
import org.yasmani.io.creditsimulator.credit.domain.model.CreditOffer;
import org.yasmani.io.creditsimulator.credit.domain.model.CreditOfferSelection;
import org.yasmani.io.creditsimulator.credit.domain.port.out.CreditOfferRepositoryPort;
import org.yasmani.io.creditsimulator.credit.domain.port.out.CreditOfferSelectionRepositoryPort;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeleccionarOfertaUseCaseTest {

  @Mock
  private CreditOfferRepositoryPort offers;

  @Mock
  private CreditOfferSelectionRepositoryPort selections;

  @InjectMocks
  private SeleccionarOfertaUseCase useCase;

  private UUID customerId;
  private CreditOffer offer;

  @BeforeEach
  void setUp() {
    customerId = UUID.randomUUID();

    offer = new CreditOffer(
        UUID.randomUUID(),
        "A1B2C3",
        customerId,
        new BigDecimal("15000.00"),
        "PEN",
        new BigDecimal("0.145"),
        24,
        OffsetDateTime.now(ZoneOffset.UTC).minusDays(1),
        OffsetDateTime.now(ZoneOffset.UTC).plusDays(30)
    );
  }

  @Test
  void shouldSelectValidOffer() {
    when(selections.existsByCustomerId(customerId))
        .thenReturn(false);

    when(offers.findActiveByCustomerId(
        eq(customerId), any(OffsetDateTime.class)
    )).thenReturn(List.of(offer));

    when(selections.save(any(CreditOfferSelection.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    CreditOfferSelection result =
        useCase.execute(customerId, "A1B2C3");

    assertNotNull(result.id());
    assertEquals(customerId, result.customerId());
    assertEquals(offer.id(), result.offerId());
    assertEquals("SELECTED", result.status());
    assertNotNull(result.selectedAt());

    verify(selections).save(any(CreditOfferSelection.class));
  }

  @Test
  void shouldRejectAlreadyProcessedCustomer() {
    when(selections.existsByCustomerId(customerId))
        .thenReturn(true);

    assertThrows(
        SeleccionarOfertaUseCase.OfertaYaProcesadaException.class,
        () -> useCase.execute(customerId, "A1B2C3")
    );

    verifyNoInteractions(offers);
    verify(selections, never())
        .save(any(CreditOfferSelection.class));
  }

  @Test
  void shouldRejectUnknownOffer() {
    when(offers.findActiveByCustomerId(
        eq(customerId), any(OffsetDateTime.class)
    )).thenReturn(List.of(offer));

    assertThrows(
        SeleccionarOfertaUseCase.OfertaNoDisponibleException.class,
        () -> useCase.execute(customerId, "UNKNOWN")
    );

    verify(selections, never())
        .save(any(CreditOfferSelection.class));
  }

  @Test
  void shouldRejectOfferFromAnotherCustomer() {
    when(offers.findActiveByCustomerId(
        eq(customerId), any(OffsetDateTime.class)
    )).thenReturn(List.of());

    assertThrows(
        SeleccionarOfertaUseCase.OfertaNoDisponibleException.class,
        () -> useCase.execute(customerId, "A1B2C3")
    );

    verify(selections, never())
        .save(any(CreditOfferSelection.class));
  }

  @Test
  void shouldRejectExpiredOffer() {
    // El puerto únicamente devuelve ofertas vigentes.
    when(offers.findActiveByCustomerId(
        eq(customerId), any(OffsetDateTime.class)
    )).thenReturn(List.of());

    assertThrows(
        SeleccionarOfertaUseCase.OfertaNoDisponibleException.class,
        () -> useCase.execute(customerId, "A1B2C3")
    );

    verify(selections, never())
        .save(any(CreditOfferSelection.class));
  }

  @Test
  void shouldPropagatePersistenceFailure() {
    when(offers.findActiveByCustomerId(
        eq(customerId), any(OffsetDateTime.class)
    )).thenReturn(List.of(offer));

    when(selections.save(any(CreditOfferSelection.class)))
        .thenThrow(new IllegalStateException("Database failure"));

    assertThrows(
        IllegalStateException.class,
        () -> useCase.execute(customerId, "A1B2C3")
    );

    verify(selections).save(any(CreditOfferSelection.class));
  }
}

