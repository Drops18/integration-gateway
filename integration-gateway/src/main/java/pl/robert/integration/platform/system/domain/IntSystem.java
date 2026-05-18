package pl.robert.integration.platform.system.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.robert.integration.platform.common.domain.AuditableEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INTEGRATION_SYSTEMS")
public class IntSystem extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 30)
  private String name;

  @Column(nullable = false)
  private Boolean active = true;

  @Column(name = "API_KEY", nullable = false, unique = true, length = 128)
  private String apiKey;

}
