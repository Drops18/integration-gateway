package pl.robert.integration.platform.system.dto;

import pl.robert.integration.platform.common.dto.AuditDto;

public record IntSystemResponseDto(
  Long id,
  String name,
  Boolean active,
  String maskedApiKey,
  AuditDto audit
) { }