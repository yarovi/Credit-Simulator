package org.yasmani.io.creditsimulator.customer.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "customers")
public class CustomerEntity {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "document_number", nullable = false, length = 8)
  private String documentNumber;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "email")
  private String email;

  @Column(name = "phone")
  private String phone;

  @JdbcTypeCode(SqlTypes.CHAR)
  @Column(name = "risk_status", nullable = false, length = 1)
  private String riskStatus;

  protected CustomerEntity() {
    // Constructor requerido por JPA.
  }

  public UUID getId() {
    return id;
  }

  public String getDocumentNumber() {
    return documentNumber;
  }

  public String getFullName() {
    return fullName;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public String getRiskStatus() {
    return riskStatus;
  }
}
