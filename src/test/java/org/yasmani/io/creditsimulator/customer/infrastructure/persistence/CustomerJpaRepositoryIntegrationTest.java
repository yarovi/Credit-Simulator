package org.yasmani.io.creditsimulator.customer.infrastructure.persistence;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CustomerJpaRepositoryIntegrationTest {
  @Autowired
  private CustomerJpaRepository customerJpaRepository;

  @Test
  void shouldFindCustomerByDocumentNumber() {

    // Arrange: DNI existente en customer_db
    String documentNumber = "70000001";

    // Act: consulta real mediante Spring Data JPA
    var result = customerJpaRepository
        .findByDocumentNumber(documentNumber);

    // Assert: verificamos el cliente recuperado
    assertTrue(result.isPresent());

    CustomerEntity customer = result.get();

    assertEquals("70000001", customer.getDocumentNumber());
    assertEquals("Ana Torres", customer.getFullName());
    assertEquals("N", customer.getRiskStatus());
  }
}
