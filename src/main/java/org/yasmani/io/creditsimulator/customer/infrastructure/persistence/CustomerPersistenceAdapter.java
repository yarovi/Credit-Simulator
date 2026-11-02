package org.yasmani.io.creditsimulator.customer.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import org.yasmani.io.creditsimulator.customer.domain.model.Customer;
import org.yasmani.io.creditsimulator.customer.domain.port.CustomerRepositoryPort;

import java.util.Optional;

@Repository
public class CustomerPersistenceAdapter implements CustomerRepositoryPort {

  private final CustomerJpaRepository customerJpaRepository;

  public CustomerPersistenceAdapter(
      CustomerJpaRepository customerJpaRepository
  ) {
    this.customerJpaRepository = customerJpaRepository;
  }

  @Override
  public Optional<Customer> findByDocumentNumber(String documentNumber) {
    return customerJpaRepository.findByDocumentNumber(documentNumber)
        .map(this::toDomain);
  }

  private Customer toDomain(CustomerEntity entity) {
    return new Customer(
        entity.getId(),
        entity.getDocumentNumber(),
        entity.getFullName(),
        entity.getEmail(),
        entity.getPhone(),
        entity.getRiskStatus()
    );
  }
}
