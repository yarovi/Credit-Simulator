package org.yasmani.io.creditsimulator.access.application;

import org.springframework.stereotype.Service;
import org.yasmani.io.creditsimulator.access.domain.AccessToken;
import org.yasmani.io.creditsimulator.credit.application.usecase.ConsultarElegibilidadUseCase;
import org.yasmani.io.creditsimulator.credit.domain.model.EligibilityResult;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GenerarAccessTokenUseCase {

  private static final Duration TOKEN_TTL =
      Duration.ofMinutes(3);

  private final ConsultarElegibilidadUseCase elegibilidad;
  private final Clock clock;

  private final ConcurrentHashMap<String, AccessToken> tokens =
      new ConcurrentHashMap<>();

  public GenerarAccessTokenUseCase(
      ConsultarElegibilidadUseCase elegibilidad
  ) {
    this.elegibilidad = elegibilidad;
    this.clock = Clock.systemUTC();
  }

  public Optional<AccessToken> execute(String documentNumber) {

    return elegibilidad.execute(documentNumber)
        .filter(EligibilityResult::hasActiveOffers)
        .map(result -> {

          AccessToken token = new AccessToken(
              UUID.randomUUID().toString(),
              result.customerId(),
              clock.instant().plus(TOKEN_TTL)
          );

          tokens.put(token.value(), token);

          return token;
        });
  }

  public Optional<UUID> validate(String tokenValue) {

    if (tokenValue == null || tokenValue.isBlank()) {
      return Optional.empty();
    }

    AccessToken token = tokens.get(tokenValue);

    if (token == null) {
      return Optional.empty();
    }

    if (!clock.instant().isBefore(token.expiresAt())) {
      tokens.remove(tokenValue, token);
      return Optional.empty();
    }

    return Optional.of(token.customerId());
  }
}
