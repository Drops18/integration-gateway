package pl.robert.integration.platform.system.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robert.integration.platform.system.domain.IntSystem;

import java.util.Optional;

public interface IntSystemRepository extends JpaRepository<IntSystem, Long> {
  boolean existsByName(String name);
  boolean existsByApiKey(String apiKey);
  Optional<IntSystem> findByName(String name);
}
