package br.com.springboot.clinica.controller;

import br.com.springboot.clinica.dto.VeterinaryDto;
import br.com.springboot.clinica.entity.Attendance;
import br.com.springboot.clinica.entity.Veterinary;
import br.com.springboot.clinica.service.VeterinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Veterinary endpoint")
@CrossOrigin
@RestController
@RequestMapping("/veterinary")
public class VeterinaryController implements ControllerInterface<VeterinaryDto, Veterinary> {

  @Autowired
  VeterinaryService service;

  @Operation(summary = "Create a veterinary")
  @Override
  @PostMapping
  public ResponseEntity<Veterinary> create(@RequestBody VeterinaryDto object) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(object));
  }

  @Operation(summary = "Find a veterinary by attendance Id")
  @GetMapping("/{id}/attendance")
  public List<Attendance> getAttendances(@PathVariable Long id) {
    return service.getAttendance(id);
  }

  @Operation(summary = "Find all veterinarians")
  @Override
  @GetMapping
  public List<Veterinary> findAll() {
    return service.findAll();
  }

  @Operation(summary = "Find a veterinary by Id")
  @Override
  @GetMapping("/{id}")
  public Veterinary findById(@PathVariable Long id) {
    return service.findById(id);
  }

  @Operation(summary = "Update a veterinary by Id")
  @Override
  @PatchMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody VeterinaryDto object) {
    service.update(id, object);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(summary = "Delete a veterinary by Id")
  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
