package org.yasmani.io.creditsimulator.access.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yasmani.io.creditsimulator.access.application.GenerarAccessTokenUseCase;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/creditos/consultas")
public class AccessController {

  private final GenerarAccessTokenUseCase generarAccessToken;

  public AccessController(
      GenerarAccessTokenUseCase generarAccessToken
  ) {
    this.generarAccessToken = generarAccessToken;
  }

  @PostMapping
  public ResponseEntity<AccessResponse> consultar(
      @RequestBody AccessRequest request
  ) {
    return generarAccessToken.execute(request.documentNumber())
        .map(token -> ResponseEntity.ok(
            new AccessResponse(
                token.value(),
                token.expiresAt()
            )
        ))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  public record AccessRequest(String documentNumber) {
  }

  public record AccessResponse(
      String accessToken,
      Instant expiresAt
  ) {
  }

}
