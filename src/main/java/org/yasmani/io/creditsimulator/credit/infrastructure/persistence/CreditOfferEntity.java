package org.yasmani.io.creditsimulator.credit.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "credit_offers")
public class CreditOfferEntity {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "offer_code", nullable = false, length = 16)
  private String offerCode;

  @Column(name = "customer_id", nullable = false)
  private UUID customerId;

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @JdbcTypeCode(SqlTypes.CHAR)
  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  @Column(name = "annual_rate", nullable = false)
  private BigDecimal annualRate;

  @Column(name = "term_months", nullable = false)
  private Integer termMonths;

  @Column(name = "valid_from", nullable = false)
  private OffsetDateTime validFrom;

  @Column(name = "valid_until", nullable = false)
  private OffsetDateTime validUntil;

  protected CreditOfferEntity() {
  }

  public UUID getId() {
    return id;
  }

  public String getOfferCode() {
    return offerCode;
  }

  public UUID getCustomerId() {
    return customerId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAnnualRate() {
    return annualRate;
  }

  public Integer getTermMonths() {
    return termMonths;
  }

  public OffsetDateTime getValidFrom() {
    return validFrom;
  }

  public OffsetDateTime getValidUntil() {
    return validUntil;
  }
}
