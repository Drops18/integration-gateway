package pl.robert.integration.platform.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class AuditableEntity {

  @CreatedDate
  @Column(name = "CREATED_AT", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "CREATED_BY", nullable = false, length = 32, updatable = false)
  private String createdBy = "ADMIN";

  @LastModifiedDate
  @Column(name = "MODIFIED_AT")
  private LocalDateTime modifiedAt;

  @Column(name = "MODIFIED_BY", length = 32)
  private String modifiedBy;
}
