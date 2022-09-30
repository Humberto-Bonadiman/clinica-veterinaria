package br.com.springboot.clinica.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "attendance")
public class Attendance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "veterinary_id")
  private Veterinary veterinary;

  @ManyToOne
  @JoinColumn(name = "animal_id")
  private Animal animal;

  @Column(name = "reason_attendance", nullable = false)
  @ElementCollection(targetClass = String.class)
  private List<String> reasonAttendance;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Veterinary getVeterinary() {
    return veterinary;
  }

  public void setVeterinary(Veterinary veterinary) {
    this.veterinary = veterinary;
  }

  public Animal getAnimal() {
    return animal;
  }

  public void setAnimal(Animal animal) {
    this.animal = animal;
  }

  public List<String> getReasonAttendance() {
    return reasonAttendance;
  }

  public void setReasonAttendance(List<String> reasonAttendance) {
    this.reasonAttendance = reasonAttendance;
  }
}
