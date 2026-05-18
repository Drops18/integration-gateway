package pl.robert.integration.platform.shipment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robert.integration.platform.shipment.domain.AttVersion;

public interface AttVersionRepository extends JpaRepository<AttVersion, Long> {
}
