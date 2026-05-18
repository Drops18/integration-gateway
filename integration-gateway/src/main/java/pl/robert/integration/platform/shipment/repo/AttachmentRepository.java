package pl.robert.integration.platform.shipment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robert.integration.platform.shipment.domain.Attachment;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
  List<Attachment> findAllByShipmentId(Long shipmentId);
}
