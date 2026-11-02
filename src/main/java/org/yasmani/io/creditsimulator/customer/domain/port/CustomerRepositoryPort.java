package org.yasmani.io.creditsimulator.customer.domain.port;

import org.yasmani.io.creditsimulator.customer.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepositoryPort {
  Optional<Customer> findByDocumentNumber(String documentNumber);
}
