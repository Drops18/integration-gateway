package pl.robert.integration.platform.shipment.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.robert.integration.platform.shipment.app.ShipmentService;
import pl.robert.integration.platform.shipment.dto.AdminShipmentResponseDto;
import pl.robert.integration.platform.shipment.dto.AttachmentResponseDto;
import pl.robert.integration.platform.shipment.dto.CreateShipmentRequestDto;
import pl.robert.integration.platform.shipment.dto.ShipmentResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShipmentController {

  private final ShipmentService shipmentService;

  @PostMapping("/shipment")
  public ResponseEntity<ShipmentResponseDto> addShipment(@Valid @RequestBody CreateShipmentRequestDto request) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(shipmentService.addShipment(request));
  }

  @GetMapping("/shipment/{id}")
  public ResponseEntity<ShipmentResponseDto> getShipment(@PathVariable Long id) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(shipmentService.getShipment(id));
  }

  @GetMapping("/admin/shipment")
  public ResponseEntity<List<AdminShipmentResponseDto>> getAllShipments() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(shipmentService.getAllShipments());
  }

  @DeleteMapping("/admin/shipment/{id}")
  public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {
    shipmentService.deleteShipment(id);

    return ResponseEntity
      .status(HttpStatus.NO_CONTENT)
      .build();
  }

  @PostMapping("/shipment/{shipmentId}/attachment")
  public ResponseEntity<AttachmentResponseDto> addAttachment(@PathVariable Long shipmentId, @RequestParam("file") MultipartFile file) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(shipmentService.addAttachment(shipmentId, file));
  }

}
