package pl.robert.integration.platform.system.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.robert.integration.platform.system.app.IntSystemService;
import pl.robert.integration.platform.system.dto.CreateIntSystemRequestDto;
import pl.robert.integration.platform.system.dto.IntSystemResponseDto;
import pl.robert.integration.platform.system.dto.UpdateIntSystemRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IntSystemController {
  private final IntSystemService intSystemService;

  @GetMapping("/admin/system")
  public ResponseEntity<List<IntSystemResponseDto>> getSystem() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(intSystemService.getAllSystems());
  }

  @GetMapping("/admin/system/{id}")
  public ResponseEntity<IntSystemResponseDto> getSystem(@PathVariable Long id) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(intSystemService.getSystem(id));
  }

  @PostMapping("/admin/system")
  public ResponseEntity<IntSystemResponseDto> addSystem(@Valid @RequestBody CreateIntSystemRequestDto request) {
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(intSystemService.addSystem(request));
  }

  @PutMapping("/admin/system/{id}")
  public ResponseEntity<IntSystemResponseDto> updateSystem(@PathVariable Long id, @Valid @RequestBody UpdateIntSystemRequestDto request) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(intSystemService.updateSystem(id, request));
  }

  @DeleteMapping("/admin/system/{id}")
  public ResponseEntity<Void> deleteSystem(@PathVariable Long id) {
    intSystemService.deleteSystem(id);

    return ResponseEntity
      .status(HttpStatus.NO_CONTENT)
      .build();
  }

}
