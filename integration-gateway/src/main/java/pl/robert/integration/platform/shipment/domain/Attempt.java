package pl.robert.integration.platform.shipment.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.robert.integration.platform.common.domain.AuditableEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "ATTEMPTS")
public class Attempt extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "SHIPMENT_ID", nullable = false)
  private Shipment shipment;

  @Column(name = "ATTEMPT_NO", nullable = false)
  private Integer attemptNo = 0;

  @Column(nullable = false)
  private Boolean success;

  @Column(name = "HTTP_CODE", nullable = false)
  private Integer httpCode;

  @Column(name = "RESPONSE_MSG", length = 32767)
  private String responseMsg;

  @Column(name = "START_AT", nullable = false)
  private LocalDateTime startAt;

  @Column(name = "END_AT", nullable = false)
  private LocalDateTime endAt;

  @Column(nullable = false)
  private Float duration;

}
