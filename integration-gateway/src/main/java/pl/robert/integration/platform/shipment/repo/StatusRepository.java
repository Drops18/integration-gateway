package pl.robert.integration.platform.shipment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robert.integration.platform.shipment.domain.Status;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {
  List<Status> findAllByShipmentId(Long shipmentId);
}
