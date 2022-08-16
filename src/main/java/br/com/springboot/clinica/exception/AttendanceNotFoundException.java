package br.com.springboot.clinica.exception;

public class AttendanceNotFoundException extends RuntimeException  {
  private static final long serialVersionUID = 1L;

  public AttendanceNotFoundException(String message) {
    super("No attendance found with id: " + message);
  }
}
