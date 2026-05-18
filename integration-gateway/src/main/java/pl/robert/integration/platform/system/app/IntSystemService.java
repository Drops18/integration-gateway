package pl.robert.integration.platform.system.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.robert.integration.platform.common.dto.AuditDto;
import pl.robert.integration.platform.common.exception.DuplicateResourceException;
import pl.robert.integration.platform.common.exception.ResourceNotFoundException;
import pl.robert.integration.platform.system.domain.IntSystem;
import pl.robert.integration.platform.system.dto.CreateIntSystemRequestDto;
import pl.robert.integration.platform.system.dto.IntSystemResponseDto;
import pl.robert.integration.platform.system.dto.UpdateIntSystemRequestDto;
import pl.robert.integration.platform.system.repo.IntSystemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IntSystemService {

  private final IntSystemRepository intSystemRepository;

  private IntSystemResponseDto mapToResponseDto(IntSystem system) {
    return new IntSystemResponseDto(
      system.getId(),
      system.getName(),
      system.getActive(),
      system.getApiKey(),
      new AuditDto(
        system.getCreatedAt(),
        system.getCreatedBy(),
        system.getModifiedAt(),
        system.getModifiedBy()
      )
    );
  }

  private IntSystem mapToIntSystem(CreateIntSystemRequestDto requestDto) {
    return IntSystem
      .builder()
      .name(requestDto.name())
      .active(true)
      .apiKey(requestDto.apiKey())
      .build();
  }

  @Transactional(readOnly = true)
  public List<IntSystemResponseDto> getAllSystems() {
    return intSystemRepository.findAll()
      .stream()
      .map(this::mapToResponseDto)
      .toList();
  }

  @Transactional(readOnly = true)
  public IntSystemResponseDto getSystem(Long id) {
    return intSystemRepository.findById(id)
      .map(this::mapToResponseDto)
      .orElseThrow(
        () -> new ResourceNotFoundException("Nie znaleziono systemu o ID = " + id)
      );
  }

  @Transactional
  public IntSystemResponseDto addSystem(CreateIntSystemRequestDto request) {
    IntSystem sys = mapToIntSystem(request);

    if (intSystemRepository.existsByName(sys.getName())) {
      throw new DuplicateResourceException("System o nazwie " + sys.getName() + " już istnieje");
    }

    if (intSystemRepository.existsByApiKey(sys.getApiKey())) {
      throw new DuplicateResourceException("Podany klucz API " + sys.getApiKey() + " jest już wykorzystywany");
    }

    intSystemRepository.save(sys);

    return mapToResponseDto(sys);
  }

  @Transactional
  public IntSystemResponseDto updateSystem(Long id, UpdateIntSystemRequestDto request) {
    IntSystem sys = intSystemRepository.findById(id)
                      .orElseThrow(
                        () -> new ResourceNotFoundException("Nie znaleziono systemu o ID = " + id)
                      );

    sys.setName(request.name());
    sys.setApiKey(request.apiKey());
    sys.setActive(request.active());

    intSystemRepository.save(sys);

    return mapToResponseDto(sys);
  }

  @Transactional
  public void deleteSystem(Long id) {
    IntSystem sys = intSystemRepository.findById(id)
      .orElseThrow(
        () -> new ResourceNotFoundException("Nie znaleziono systemu o ID = " + id)
      );

    intSystemRepository.delete(sys);
  }
}
