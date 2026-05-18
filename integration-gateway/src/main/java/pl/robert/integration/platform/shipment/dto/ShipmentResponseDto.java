package pl.robert.integration.platform.shipment.dto;

import java.util.List;

public record ShipmentResponseDto(
  Long id,
  String title,
  String content,
  Long systemIdFrom,
  Long systemIdTo,
  String description,
  Integer attemptsNo,
  Long srcRequestId,
  List<StatusResponseDto> statuses,
  List<AttachmentResponseDto> attachments
) { }