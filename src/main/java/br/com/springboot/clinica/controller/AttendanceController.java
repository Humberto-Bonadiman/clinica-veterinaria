package br.com.springboot.clinica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.clinica.dto.AttendanceDto;
import br.com.springboot.clinica.entity.Attendance;
import br.com.springboot.clinica.service.AttendanceService;

@CrossOrigin
@RestController
@RequestMapping("/attendance")
public class AttendanceController implements ControllerInterface<AttendanceDto, Attendance>{

  @Autowired
  AttendanceService service;

  @Override
  @PostMapping
  public ResponseEntity<Attendance> create(@RequestBody AttendanceDto object) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(object));
  }

  @Override
  @GetMapping("/{id}")
  public Attendance findById(@PathVariable Long id) {
    return service.findById(id);
  }

  @Override
  @GetMapping
  public List<Attendance> findAll() {
    return service.findAll();
  }

  @Override
  @PatchMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody AttendanceDto object) {
    service.update(id, object);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Override
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}