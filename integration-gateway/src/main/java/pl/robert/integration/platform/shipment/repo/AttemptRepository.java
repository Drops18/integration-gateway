package pl.robert.integration.platform.shipment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robert.integration.platform.shipment.domain.Attempt;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
}
