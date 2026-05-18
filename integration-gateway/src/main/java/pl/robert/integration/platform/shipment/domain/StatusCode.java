package pl.robert.integration.platform.shipment.domain;

public enum StatusCode {
  NEW,
  VALIDATED,
  QUEUED,
  PROCESSING,
  SENT,
  DELIVERED,
  FAILED,
  RETRY_SCHEDULED,
  CANCELLED
}
