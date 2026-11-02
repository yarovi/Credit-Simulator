package org.yasmani.io.creditsimulator.customer.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.yasmani.io.creditsimulator.customer.domain.port.CustomerRepositoryPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
public class CustomerPersistenceAdapterIntegrationTest {
  @Autowired
  private CustomerRepositoryPort customerRepositoryPort;

  @Test
  void shouldFindCustomerUsingDomainPort() {

    var result = customerRepositoryPort
        .findByDocumentNumber("70000001");

    assertTrue(result.isPresent());

    var customer = result.get();

    assertEquals("Ana Torres", customer.fullName());
    assertEquals("N", customer.riskStatus());
  }
}
