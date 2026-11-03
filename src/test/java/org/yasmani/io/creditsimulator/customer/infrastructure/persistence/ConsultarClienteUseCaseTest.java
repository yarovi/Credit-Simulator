package org.yasmani.io.creditsimulator.customer.infrastructure.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yasmani.io.creditsimulator.customer.application.usecase.ConsultarClienteUseCase;
import org.yasmani.io.creditsimulator.customer.domain.model.Customer;
import org.yasmani.io.creditsimulator.customer.domain.port.CustomerRepositoryPort;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ConsultarClienteUseCaseTest {
  private CustomerRepositoryPort customerRepositoryPort;
  private ConsultarClienteUseCase useCase;

  @BeforeEach
  void setUp() {
    customerRepositoryPort = mock(CustomerRepositoryPort.class);
    useCase = new ConsultarClienteUseCase(customerRepositoryPort);
  }

  @Test
  void shouldFindExistingCustomer() {

    Customer customer = new Customer(
        UUID.fromString("10000000-0000-4000-8000-000000000001"),
        "70000001",
        "Ana Torres",
        "ana@example.com",
        "999999999",
        "N"
    );

    when(customerRepositoryPort.findByDocumentNumber("70000001"))
        .thenReturn(Optional.of(customer));

    var result = useCase.execute("70000001");

    assertTrue(result.isPresent());
    assertEquals("Ana Torres", result.get().fullName());

    verify(customerRepositoryPort)
        .findByDocumentNumber("70000001");
  }

  @Test
  void shouldReturnEmptyWhenCustomerDoesNotExist() {

    when(customerRepositoryPort.findByDocumentNumber("99999999"))
        .thenReturn(Optional.empty());

    var result = useCase.execute("99999999");

    assertTrue(result.isEmpty());

    verify(customerRepositoryPort)
        .findByDocumentNumber("99999999");
  }
}
