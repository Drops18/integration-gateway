package pl.robert.integration.platform.system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateIntSystemRequestDto(
  @NotBlank(message = "Nazwa systemu nie może być pusta")
  @Size(max = 30, message = "Nazwa systemu może mieć maksymalnie 30 znaków")
  String name,

  @NotBlank(message = "Klucz API nie może być pusty")
  @Size(max = 128, message = "Klucz API może mieć maksymalnie 128 znaków")
  String apiKey
) { }
