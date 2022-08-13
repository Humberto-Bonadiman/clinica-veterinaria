package br.com.springboot.clinica.exception;

public class GuardianNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public GuardianNotFoundException(String message) {
    super("No guardian found with id: " + message);
  }
}
