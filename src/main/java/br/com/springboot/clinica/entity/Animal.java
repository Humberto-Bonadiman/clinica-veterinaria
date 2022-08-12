package br.com.springboot.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Animal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "guardian_id")
  private Guardian guardian;

  @Column
  private String birthDate;

  @JsonIgnore
  @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<Attendance> attendance = new ArrayList<Attendance>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Guardian getGuardian() {
    return guardian;
  }

  public void setGuardian(Guardian guardian) {
    this.guardian = guardian;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public List<Attendance> getAttendance() {
    return attendance;
  }

  public void addAttendance(Attendance attendance) {
    attendance.setAnimal(this);
    this.attendance.add(attendance);
  }
}
