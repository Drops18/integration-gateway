package pl.robert.integration.platform.shipment.dto;

import pl.robert.integration.platform.shipment.domain.StatusCode;

import java.time.LocalDateTime;

public record StatusResponseDto(
  StatusCode statusCode,
  LocalDateTime creationDate
) { }
