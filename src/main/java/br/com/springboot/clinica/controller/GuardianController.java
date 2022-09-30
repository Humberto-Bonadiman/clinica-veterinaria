package br.com.springboot.clinica.controller;

import br.com.springboot.clinica.dto.GuardianDto;
import br.com.springboot.clinica.entity.Animal;
import br.com.springboot.clinica.entity.Guardian;
import br.com.springboot.clinica.service.GuardianService;
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

@Tag(name = "Guardian endpoint")
@CrossOrigin
@RestController
@RequestMapping("/guardian")
public class GuardianController implements ControllerInterface<GuardianDto, Guardian> {

  @Autowired
  GuardianService service;

  @Operation(summary = "Create a guardian")
  @Override
  @PostMapping
  public ResponseEntity<Guardian> create(@RequestBody GuardianDto object) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(object));
  }

  @Operation(summary = "Find a guardian by animal Id")
  @GetMapping("/{id}/animals")
  public List<Animal> getAnimals(@PathVariable Long id) {
    return service.getAnimals(id);
  }

  @Operation(summary = "Find all guardians")
  @Override
  @GetMapping
  public List<Guardian> findAll() {
    return service.findAll();
  }

  @Operation(summary = "Find a guardian by your Id")
  @Override
  @GetMapping("/{id}")
  public Guardian findById(@PathVariable Long id) {
    return service.findById(id);
  }

  @Operation(summary = "Update a guardian by your Id")
  @Override
  @PatchMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody GuardianDto object) {
    service.update(id, object);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(summary = "Delete a guardian by your Id")
  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
