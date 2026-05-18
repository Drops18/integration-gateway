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
@Table(name = "ATTACHMENTS")
public class Attachment extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SHIPMENT_ID", nullable = false)
  private Shipment shipment;

  @Column(name = "STORED_FILENAME", nullable = false)
  private String storedFilename;

  @Column(name = "ORIGINAL_FILENAME", nullable = false)
  private String originalFilename;

  @Column(name = "FILE_EXTENSION")
  private String fileExtension;

  @Column(name = "FILE_SIZE", nullable = false)
  private Long fileSize;

  @Column(name = "CONTENT_TYPE")
  private String contentType;
}
