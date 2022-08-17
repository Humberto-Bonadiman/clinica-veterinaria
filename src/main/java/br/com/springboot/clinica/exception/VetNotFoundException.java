package br.com.springboot.clinica.exception;

public class VetNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new Vet Not Found exception.
   *
   * @param message the message
   */
  public VetNotFoundException(String message) {
    super("No veterinarian found with id: " + message);
  }
}
