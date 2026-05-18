package pl.robert.integration.platform.shipment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robert.integration.platform.shipment.domain.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
