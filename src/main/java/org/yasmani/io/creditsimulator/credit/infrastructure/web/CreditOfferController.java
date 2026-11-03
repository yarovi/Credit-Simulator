package org.yasmani.io.creditsimulator.credit.infrastructure.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yasmani.io.creditsimulator.access.application.GenerarAccessTokenUseCase;
import org.yasmani.io.creditsimulator.credit.domain.model.CreditOffer;
import org.yasmani.io.creditsimulator.credit.domain.port.out.CreditOfferRepositoryPort;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/creditos/ofertas")
public class CreditOfferController {

  private final GenerarAccessTokenUseCase accessTokenUseCase;
  private final CreditOfferRepositoryPort creditOfferRepository;

  public CreditOfferController(
      GenerarAccessTokenUseCase accessTokenUseCase,
      CreditOfferRepositoryPort creditOfferRepository
  ) {
    this.accessTokenUseCase = accessTokenUseCase;
    this.creditOfferRepository = creditOfferRepository;
  }

  @GetMapping
  public ResponseEntity<List<CreditOffer>> consultarOfertas(
      @RequestHeader(
          value = "X-Access-Token",
          required = false
      ) String token
  ) {

    var customerId = accessTokenUseCase.validate(token);

    if (customerId.isEmpty()) {
      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .build();
    }

    UUID id = customerId.get();

    List<CreditOffer> offers =
        creditOfferRepository.findActiveByCustomerId(
            id,
            OffsetDateTime.now(ZoneOffset.UTC)
        );

    return ResponseEntity.ok(offers);
  }

}
