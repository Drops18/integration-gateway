package pl.robert.integration.platform.shipment.dto;

public record AttachmentResponseDto(
  Long id,
  String originalFilename,
  String fileExtension,
  Long fileSize,
  String contentType
) { }
