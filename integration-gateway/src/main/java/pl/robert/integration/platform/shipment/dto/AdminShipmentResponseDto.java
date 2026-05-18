package pl.robert.integration.platform.shipment.dto;

import pl.robert.integration.platform.common.dto.AuditDto;
import pl.robert.integration.platform.shipment.domain.Attachment;
import pl.robert.integration.platform.shipment.domain.Status;

import java.util.List;

public record AdminShipmentResponseDto(
  ShipmentResponseDto shipment,
  AuditDto audit
) { }