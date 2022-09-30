package br.com.springboot.clinica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "veterinary")
public class Veterinary {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "veterinary", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Attendance> attendance = new ArrayList<Attendance>();
  
  public Veterinary() {
    super();
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

  public List<Attendance> getAttendance() {
    return attendance;
  }

  public void addAttendance(Attendance attendance) {
    attendance.setVeterinary(this);
    this.attendance.add(attendance);
  }

}
