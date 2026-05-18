package pl.robert.integration.platform.common.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponseDto(
  int status,
  String error,
  Map<String, String> fieldErrors,
  LocalDateTime timestamp
) {}