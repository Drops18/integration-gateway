package pl.robert.integration.platform.shipment.domain;

import jakarta.persistence.*;
import lombok.*;
import pl.robert.integration.platform.common.domain.AuditableEntity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "ATT_VERSIONS")
public class AttVersion extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ATTACHMENT_ID", nullable = false)
  private Attachment attachment;

  @Column(name = "VERSION_NO", nullable = false)
  private Integer versionNo;

  @Column(name = "FILE_PATH", nullable = false)
  private String filePath;

  @Column(name = "IS_CURRENT", nullable = false)
  private Boolean isCurrent;
}
