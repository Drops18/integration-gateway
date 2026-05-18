package pl.robert.integration.platform.common.dto;

import lombok.ToString;

import java.time.LocalDateTime;

public record ErrorResponseDto (
  int status,
  String error,
  String message,
  LocalDateTime timestamp
) { }
