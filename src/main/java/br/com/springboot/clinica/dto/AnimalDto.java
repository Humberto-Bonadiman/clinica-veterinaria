package br.com.springboot.clinica.dto;

public class AnimalDto {

  private String name;

  private Long guardianId;

  private String birthDate;

  private String guardianName;

  public String getGuardianName() {
    return guardianName;
  }

  public void setGuardianName(String guardianName) {
   this.guardianName = guardianName;
  }

  public Long getGuardianId() {
    return guardianId;
  }

  public void setGuardianId(Long guardianId) {
    this.guardianId = guardianId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }
}
