package br.com.springboot.clinica.controller;

import br.com.springboot.clinica.dto.AnimalDto;

import br.com.springboot.clinica.entity.Animal;
import br.com.springboot.clinica.entity.Attendance;
import br.com.springboot.clinica.service.AnimalService;
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

@Tag(name = "Animal endpoint")
@CrossOrigin
@RestController
@RequestMapping("/animal")
public class AnimalController implements ControllerInterface<AnimalDto, Animal> {

  @Autowired
  AnimalService service;

  @Operation(summary = "Create a animal")
  @Override
  @PostMapping
  public ResponseEntity<Animal> create(@RequestBody AnimalDto object) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(object));
  }

  @Operation(summary = "Find a animal by your Id")
  @Override
  @GetMapping("/{id}")
  public Animal findById(@PathVariable Long id) {
    return service.findById(id);
  }

  @Operation(summary = "Find all animals")
  @Override
  @GetMapping
  public List<Animal> findAll() {
    return service.findAll();
  }

  @Operation(summary = "Find a animal by Id of attendance")
  @GetMapping("/{id}/attendance")
  public List<Attendance> getAttendances(@PathVariable Long id) {
    return service.getAttendance(id);
  }

  @Operation(summary = "Update a animal by your Id")
  @Override
  @PatchMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody AnimalDto object) {
    service.update(id, object);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(summary = "Delete a animal by your Id")
  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
