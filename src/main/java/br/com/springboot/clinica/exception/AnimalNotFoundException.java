package br.com.springboot.clinica.exception;

public class AnimalNotFoundException extends RuntimeException  {
  private static final long serialVersionUID = 1L;

  public AnimalNotFoundException(String message) {
    super("No animal found with id: " + message);
  }
}
