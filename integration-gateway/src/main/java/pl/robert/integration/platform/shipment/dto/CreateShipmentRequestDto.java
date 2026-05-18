package pl.robert.integration.platform.shipment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateShipmentRequestDto(

  @NotBlank(message = "Tytuł wysyłki nie może być pusty")
  @Size(max = 128, message = "Tytuł wysyłki może mieć maksymalnie 128 znaków")
  String title,

  @Size(max = 2000, message = "Treść wysyłki może mieć maksymalnie 2000 znaków")
  String content,

  @NotNull(message = "System źródłowy nie może być pusty")
  String systemNameFrom,

  @NotNull(message = "System docelowy nie może być pusty")
  String systemNameTo,

  @Size(max = 255, message = "Opis może mieć maksymalnie 255 znaków")
  String description,

  @NotNull(message = "Id z systemu źródłowego nie może być puste")
  Long srcRequestId

) { }