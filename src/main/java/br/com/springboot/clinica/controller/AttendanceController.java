package br.com.springboot.clinica.controller;

import br.com.springboot.clinica.dto.AttendanceDto;
import br.com.springboot.clinica.entity.Attendance;
import br.com.springboot.clinica.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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

@Tag(name = "Attendance endpoint")
@CrossOrigin
@RestController
@RequestMapping("/attendance")
public class AttendanceController {

  @Autowired
  AttendanceService service;

  @Operation(summary = "Create a attendance")
  @PostMapping
  public ResponseEntity<Attendance> create(@RequestBody AttendanceDto object) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(object));
  }

  @Operation(summary = "Get a attendance by Id")
  @GetMapping("/{id}")
  public Attendance findById(@PathVariable Long id) {
    return service.findById(id);
  }

  @Operation(summary = "Get all attendance")
  @GetMapping
  public List<Attendance> findAll() {
    return service.findAll();
  }

  @Operation(summary = "Update a attendance by Id")
  @PatchMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody AttendanceDto object) {
    service.update(id, object);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
