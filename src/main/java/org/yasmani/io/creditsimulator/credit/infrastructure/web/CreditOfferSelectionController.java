package org.yasmani.io.creditsimulator.credit.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yasmani.io.creditsimulator.access.application.GenerarAccessTokenUseCase;
import org.yasmani.io.creditsimulator.credit.application.usecase.SeleccionarOfertaUseCase;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/creditos/ofertas/selecciones")
public class CreditOfferSelectionController {

  private final GenerarAccessTokenUseCase tokenUseCase;
  private final SeleccionarOfertaUseCase seleccionarOferta;

  public CreditOfferSelectionController(
      GenerarAccessTokenUseCase tokenUseCase,
      SeleccionarOfertaUseCase seleccionarOferta
  ) {
    this.tokenUseCase = tokenUseCase;
    this.seleccionarOferta = seleccionarOferta;
  }

  @PostMapping
  public ResponseEntity<?> seleccionar(
      @RequestHeader(
          value = "X-Access-Token",
          required = false
      ) String token,
      @RequestBody SelectionRequest request
  ) {
    var customerId = tokenUseCase.validate(token);

    if (customerId.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Map.of(
              "code", "INVALID_TOKEN",
              "message", "Token inválido o vencido"
          ));
    }

    if (request.offerCode() == null ||
        request.offerCode().isBlank()) {
      return ResponseEntity.badRequest()
          .body(Map.of(
              "code", "INVALID_OFFER_CODE",
              "message", "El código de oferta es obligatorio"
          ));
    }

    try {
      var selection = seleccionarOferta.execute(
          customerId.get(),
          request.offerCode()
      );

      return ResponseEntity.status(HttpStatus.CREATED)
          .body(selection);

    } catch (SeleccionarOfertaUseCase.OfertaYaProcesadaException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(Map.of(
              "code", "OFFER_ALREADY_PROCESSED",
              "message",
              "Ya se procesó una selección de oferta para este cliente. No es posible seleccionar otra oferta."
          ));

    } catch (SeleccionarOfertaUseCase.OfertaNoDisponibleException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Map.of(
              "code", "OFFER_NOT_AVAILABLE",
              "message",
              "La oferta no existe, no pertenece al cliente o ya venció"
          ));
    }
  }

  public record SelectionRequest(String offerCode) {
  }
}
