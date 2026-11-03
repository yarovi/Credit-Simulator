package org.yasmani.io.creditsimulator.customer.application.usecase;

import org.springframework.stereotype.Service;
import org.yasmani.io.creditsimulator.customer.domain.model.Customer;
import org.yasmani.io.creditsimulator.customer.domain.port.CustomerRepositoryPort;

import java.util.Optional;

@Service
public class ConsultarClienteUseCase {
  private final CustomerRepositoryPort customerRepositoryPort;

  public ConsultarClienteUseCase(
      CustomerRepositoryPort customerRepositoryPort
  ) {
    this.customerRepositoryPort = customerRepositoryPort;
  }

  public Optional<Customer> execute(String documentNumber) {
    return customerRepositoryPort.findByDocumentNumber(documentNumber);
  }
}
