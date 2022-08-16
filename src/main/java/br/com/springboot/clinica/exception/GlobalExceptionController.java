package br.com.springboot.clinica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

  @ExceptionHandler({
      AnimalNotFoundException.class,
      AttendanceNotFoundException.class,
      GuardianNotFoundException.class,
      VetNotFoundException.class,
  })
  public ResponseEntity<String> handleRecursoNaoEncontradoException(
      RuntimeException exception) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(exception.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<String> handleThrowable(Throwable exception) {
    return ResponseEntity
        .status(HttpStatus.BAD_GATEWAY)
        .body(exception.getMessage());
  }
}
