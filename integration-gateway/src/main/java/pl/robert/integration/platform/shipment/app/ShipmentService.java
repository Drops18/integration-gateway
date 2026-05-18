package pl.robert.integration.platform.shipment.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.robert.integration.platform.common.dto.AuditDto;
import pl.robert.integration.platform.common.exception.InvalidOperationException;
import pl.robert.integration.platform.common.exception.ResourceNotFoundException;
import pl.robert.integration.platform.shipment.domain.*;
import pl.robert.integration.platform.shipment.dto.*;
import pl.robert.integration.platform.shipment.repo.AttVersionRepository;
import pl.robert.integration.platform.shipment.repo.AttachmentRepository;
import pl.robert.integration.platform.shipment.repo.ShipmentRepository;
import pl.robert.integration.platform.shipment.repo.StatusRepository;
import pl.robert.integration.platform.system.domain.IntSystem;
import pl.robert.integration.platform.system.repo.IntSystemRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentService {
  private final ShipmentRepository shipmentRepository;
  private final IntSystemRepository intSystemRepository;
  private final StatusRepository statusRepository;
  private final AttachmentRepository attachmentRepository;
  private final AttVersionRepository attVersionRepository;
  private final ShipmentStatusService shipmentStatusService;

  private Shipment mapToShipment(CreateShipmentRequestDto requestDto, IntSystem systemFrom, IntSystem systemTo) {
    return Shipment
      .builder()
      .title(requestDto.title())
      .content(requestDto.content())
      .systemFrom(systemFrom)
      .systemTo(systemTo)
      .attemptsNo(0)
      .description(requestDto.description())
      .srcRequestId(requestDto.srcRequestId())
      .build();
  }

  private ShipmentResponseDto mapToShipmentResponseDto(Shipment shipment) {
    return new ShipmentResponseDto(
      shipment.getId(),
      shipment.getTitle(),
      shipment.getContent(),
      shipment.getSystemFrom().getId(),
      shipment.getSystemTo().getId(),
      shipment.getDescription(),
      shipment.getAttemptsNo(),
      shipment.getSrcRequestId(),
      statusRepository
        .findAllByShipmentId(shipment.getId())
        .stream()
        .map(this::mapToStatusResponseDto)
        .toList(),
      attachmentRepository
        .findAllByShipmentId(shipment.getId())
        .stream()
        .map(this::mapToAttachmentResponseDto)
        .toList()
    );
  }

  private StatusResponseDto mapToStatusResponseDto(Status status) {
    return new StatusResponseDto(
      status.getStatusCode(),
      status.getCreatedAt()
    );
  }

  private String getFileExtension(String filename) {
    int dotIndex = filename.lastIndexOf(".");

    if (dotIndex == -1 || dotIndex == filename.length() - 1) {
      return null;
    }

    return filename.substring(dotIndex + 1);
  }

  private AdminShipmentResponseDto mapToAdminShipmentResponseDto(Shipment shipment) {
    return new AdminShipmentResponseDto(
      mapToShipmentResponseDto(shipment),
      new AuditDto(
        shipment.getCreatedAt(),
        shipment.getCreatedBy(),
        shipment.getModifiedAt(),
        shipment.getModifiedBy()
      )
    );
  }

  private AttachmentResponseDto mapToAttachmentResponseDto(Attachment attachment) {
    return new AttachmentResponseDto(
      attachment.getId(),
      attachment.getOriginalFilename(),
      attachment.getFileExtension(),
      attachment.getFileSize(),
      attachment.getContentType()
    );
  }

  @Transactional
  public ShipmentResponseDto addShipment(CreateShipmentRequestDto request) {
    if (request.systemNameTo().equals(request.systemNameFrom())) {
      throw new InvalidOperationException("Systemy źródłowy i docelowy nie mogą być takie same");
    }

    IntSystem systemFrom = intSystemRepository
      .findByName(request.systemNameFrom())
      .orElseThrow(
        () -> new ResourceNotFoundException("Nie znaleziono systemu źródłowego o nazwie " + request.systemNameFrom())
      );

    IntSystem systemTo = intSystemRepository
      .findByName(request.systemNameTo())
      .orElseThrow(
        () -> new ResourceNotFoundException("Nie znaleziono systemu docelowego o nazwie " + request.systemNameFrom())
      );

    Shipment shipment = mapToShipment(request, systemFrom, systemTo);

    shipmentRepository.save(shipment);

    shipmentStatusService.addStatus(shipment, StatusCode.NEW);

    return mapToShipmentResponseDto(shipment);
  }

  @Transactional(readOnly = true)
  public ShipmentResponseDto getShipment(Long id) {
    Shipment shipment =  shipmentRepository
      .findById(id)
      .orElseThrow(
        () -> new ResourceNotFoundException("Nie znaleziono przesyłki o ID " + id)
      );

    return mapToShipmentResponseDto(shipment);
  }

  @Transactional(readOnly = true)
  public List<AdminShipmentResponseDto> getAllShipments() {
    return shipmentRepository.findAll()
      .stream()
      .map(this::mapToAdminShipmentResponseDto)
      .toList();
  }

  @Transactional
  public void deleteShipment(Long id) {
    Shipment shipment = shipmentRepository.findById(id)
      .orElseThrow(
        () -> new ResourceNotFoundException("Nie znaleziono przesyłki o ID = " + id)
      );

    shipmentRepository.delete(shipment);
  }

  @Transactional
  public AttachmentResponseDto addAttachment(Long shipmentId, MultipartFile file) {
    if (file.isEmpty()) {
      throw new InvalidOperationException("Plik jest pusty");
    }

    Shipment shipment = shipmentRepository.findById(shipmentId)
      .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono przesyłki o ID = " + shipmentId));

    String originalFilename = file.getOriginalFilename();

    if (originalFilename == null || originalFilename.isBlank()) {
      throw new InvalidOperationException("Nazwa pliku jest pusta");
    }

    String fileExtension = getFileExtension(originalFilename);
    String storedFilename = UUID.randomUUID() + "_" + originalFilename;

    Path uploadDir = Paths.get("uploads", "shipments", shipmentId.toString());
    Path targetPath = uploadDir.resolve(storedFilename);

    try {
      Files.createDirectories(uploadDir);
      file.transferTo(targetPath);
    } catch (IOException e) {
      throw new RuntimeException("Błąd podczas zapisu pliku", e);
    }

    Attachment attachment = Attachment.builder()
      .shipment(shipment)
      .storedFilename(storedFilename)
      .originalFilename(originalFilename)
      .fileExtension(fileExtension)
      .fileSize(file.getSize())
      .contentType(file.getContentType())
      .build();

    attachmentRepository.save(attachment);

    AttVersion version = AttVersion.builder()
      .attachment(attachment)
      .versionNo(1)
      .filePath(targetPath.toString())
      .isCurrent(true)
      .build();

    attVersionRepository.save(version);

    return mapToAttachmentResponseDto(attachment);
  }

}
