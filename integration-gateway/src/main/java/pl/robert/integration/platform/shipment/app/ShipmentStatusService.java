package pl.robert.integration.platform.shipment.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.robert.integration.platform.shipment.domain.Shipment;
import pl.robert.integration.platform.shipment.domain.Status;
import pl.robert.integration.platform.shipment.domain.StatusCode;
import pl.robert.integration.platform.shipment.repo.StatusRepository;

@Service
@RequiredArgsConstructor
public class ShipmentStatusService {

  private final StatusRepository statusRepository;

  public void addStatus(Shipment shipment, StatusCode statusCode) {
    Status status = Status
      .builder()
      .shipment(shipment)
      .statusCode(statusCode)
      .build();

    statusRepository.save(status);
  }
}
