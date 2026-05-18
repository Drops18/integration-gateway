package pl.robert.integration.platform.shipment.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.robert.integration.platform.common.domain.AuditableEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "STATUSES")
public class Status extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SHIPMENT_ID", nullable = false)
  private Shipment shipment;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS_CODE", nullable = false, length = 32)
  private StatusCode statusCode;

}
