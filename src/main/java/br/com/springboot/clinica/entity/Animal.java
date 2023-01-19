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
import javax.persistence.Table;

@Entity
@Table(name = "animal")
public class Animal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String species;

  @Column
  private String race;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "guardian_id")
  private Guardian guardian;

  @Column
  private String birthDate;

  @JsonIgnore
  @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Attendance> attendance = new ArrayList<Attendance>();
  
  public Animal() {
    super();
    this.attendance = new ArrayList<Attendance>();
  }
  
  /**
   * constructor animal.
   */
  public Animal(String name, String species, String race, String birthDate) {
    this.name = name;
    this.species = species;
    this.race = race;
    this.birthDate = birthDate;
    this.attendance = new ArrayList<Attendance>();
  }

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

  public String getSpecies() {
    return species;
  }

  public void setSpecies(String species) {
    this.species = species;
  }

  public String getRace() {
    return race;
  }

  public void setRace(String race) {
    this.race = race;
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
