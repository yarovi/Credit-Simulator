package org.yasmani.io.creditsimulator.access.application;

import org.junit.jupiter.api.Test;
import org.yasmani.io.creditsimulator.access.application.GenerarAccessTokenUseCase;
import org.yasmani.io.creditsimulator.access.domain.AccessToken;
import org.yasmani.io.creditsimulator.credit.application.usecase.ConsultarElegibilidadUseCase;
import org.yasmani.io.creditsimulator.credit.domain.model.EligibilityResult;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenerarAccessTokenUseCaseTest {

  private final ConsultarElegibilidadUseCase elegibilidad =
      mock(ConsultarElegibilidadUseCase.class);

  private final GenerarAccessTokenUseCase useCase =
      new GenerarAccessTokenUseCase(elegibilidad);

  @Test
  void shouldGenerateTokenForEligibleCustomer() {

    UUID customerId = UUID.randomUUID();

    when(elegibilidad.execute("70000001"))
        .thenReturn(Optional.of(
            new EligibilityResult(customerId, true, 3)
        ));

    var result = useCase.execute("70000001");

    assertTrue(result.isPresent());
    assertNotNull(result.get().value());
    assertNotNull(result.get().expiresAt());
  }

  @Test
  void shouldNotGenerateTokenWithoutOffers() {

    when(elegibilidad.execute("70000003"))
        .thenReturn(Optional.of(
            new EligibilityResult(
                UUID.randomUUID(), false, 0
            )
        ));

    assertTrue(useCase.execute("70000003").isEmpty());
  }

  @Test
  void shouldNotGenerateTokenForUnknownCustomer() {

    when(elegibilidad.execute("99999999"))
        .thenReturn(Optional.empty());

    assertTrue(useCase.execute("99999999").isEmpty());
  }

  @Test
  void shouldValidateIssuedToken() {

    UUID customerId = UUID.randomUUID();

    when(elegibilidad.execute("70000001"))
        .thenReturn(Optional.of(
            new EligibilityResult(customerId, true, 3)
        ));

    AccessToken token = useCase.execute("70000001")
        .orElseThrow();

    Optional<UUID> result = useCase.validate(token.value());

    assertEquals(Optional.of(customerId), result);
  }

  @Test
  void shouldRejectUnknownToken() {

    Optional<UUID> result = useCase.validate("unknown-token");

    assertTrue(result.isEmpty());
  }

}
