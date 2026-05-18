package pl.robert.integration.platform.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.robert.integration.platform.common.dto.ErrorResponseDto;
import pl.robert.integration.platform.common.dto.ValidationErrorResponseDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleNotFoundEx(ResourceNotFoundException ex) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage());
  }

  @ExceptionHandler(InvalidOperationException.class)
  public ResponseEntity<ErrorResponseDto> handleInvalidOperationEx(InvalidOperationException ex) {
    return buildErrorResponse(HttpStatus.CONFLICT, "Invalid operation", ex.getMessage());
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ErrorResponseDto> handleDuplicateResourceEx(DuplicateResourceException ex) {
    return buildErrorResponse(HttpStatus.CONFLICT, "Duplicate resource", ex.getMessage());
  }

  @ExceptionHandler(ExternalSystemException.class)
  public ResponseEntity<ErrorResponseDto> handleExternalSystemEx(ExternalSystemException ex) {
    return buildErrorResponse(HttpStatus.BAD_GATEWAY, "External system error", ex.getMessage());
  }

  @ExceptionHandler(ExternalSystemTimeoutException.class)
  public ResponseEntity<ErrorResponseDto> handleExternalTimeout(ExternalSystemTimeoutException ex) {
    return buildErrorResponse(HttpStatus.GATEWAY_TIMEOUT, "External system timeout", ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponseDto> handleValidation(
    MethodArgumentNotValidException ex
  ) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
      .getFieldErrors()
      .forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
      );

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(new ValidationErrorResponseDto(
        HttpStatus.BAD_REQUEST.value(),
        "Validation error",
        errors,
        LocalDateTime.now()
      ));
  }

  private ResponseEntity<ErrorResponseDto> buildErrorResponse(HttpStatus status, String error, String message) {
    return ResponseEntity
      .status(status)
      .body(new ErrorResponseDto(
        status.value(),
        error,
        message,
        LocalDateTime.now()
      ));
  }

}
