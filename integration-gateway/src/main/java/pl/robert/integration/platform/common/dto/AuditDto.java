package pl.robert.integration.platform.common.dto;

import java.time.LocalDateTime;

public record AuditDto(
  LocalDateTime createdAt,
  String createdBy,
  LocalDateTime modifiedAt,
  String modifiedBy
) { }
