package pl.robert.integration.platform.shipment.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.robert.integration.platform.common.domain.AuditableEntity;
import pl.robert.integration.platform.system.domain.IntSystem;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "SHIPMENTS")
public class Shipment extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 128, nullable = false)
  private String title;

  @Column(length = 2000)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SYSTEM_ID_FROM", nullable = false)
  private IntSystem systemFrom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SYSTEM_ID_TO", nullable = false)
  private IntSystem systemTo;

  private String description;

  private Integer attemptsNo;

  @Column(name = "SRC_REQUEST_ID", nullable = false)
  private Long srcRequestId;

  @Override
  public String toString() {
    return "TODO";
  }

}
