package br.com.springboot.clinica.dto;

import java.util.List;

public class AttendanceDto {

  private Long veterinaryId;

  private Long animalId;

  private List<String> reasonAttendance;

  public Long getVeterinaryId() {
    return veterinaryId;
  }

  public void setVeterinaryId(Long veterinaryId) {
    this.veterinaryId = veterinaryId;
  }

  public Long getAnimalId() {
    return animalId;
  }

  public void setAnimalId(Long animalId) {
    this.animalId = animalId;
  }

  public List<String> getReasonAttendance() {
    return reasonAttendance;
  }

  public void setReasonAttendance(List<String> reasonAttendance) {
    this.reasonAttendance = reasonAttendance;
  }
}
