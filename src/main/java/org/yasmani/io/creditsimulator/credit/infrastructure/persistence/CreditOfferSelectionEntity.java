package org.yasmani.io.creditsimulator.credit.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "credit_offer_selection")
public class CreditOfferSelectionEntity {

  @Id
  private UUID id;

  @Column(name = "customer_id", nullable = false)
  private UUID customerId;

  @Column(name = "offer_id", nullable = false)
  private UUID offerId;

  @Column(name = "selected_at", nullable = false)
  private OffsetDateTime selectedAt;

  @Column(name = "status", nullable = false)
  private String status;

  protected CreditOfferSelectionEntity() {
  }

  public CreditOfferSelectionEntity(
      UUID id,
      UUID customerId,
      UUID offerId,
      OffsetDateTime selectedAt,
      String status
  ) {
    this.id = id;
    this.customerId = customerId;
    this.offerId = offerId;
    this.selectedAt = selectedAt;
    this.status = status;
  }

  public UUID getId() { return id; }
  public UUID getCustomerId() { return customerId; }
  public UUID getOfferId() { return offerId; }
  public OffsetDateTime getSelectedAt() { return selectedAt; }
  public String getStatus() { return status; }
}
