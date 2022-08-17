package br.com.springboot.clinica.controller;

import br.com.springboot.clinica.dto.GuardianDto;
import br.com.springboot.clinica.entity.Animal;
import br.com.springboot.clinica.entity.Guardian;
import br.com.springboot.clinica.service.GuardianService;
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

@CrossOrigin
@RestController
@RequestMapping("/guardian")
public class GuardianController implements ControllerInterface<GuardianDto, Guardian> {

  @Autowired
  GuardianService service;

  @Override
  @PostMapping
  public ResponseEntity<Guardian> create(@RequestBody GuardianDto object) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(object));
  }

  @GetMapping("/{id}/animals")
  public List<Animal> getAnimals(@PathVariable Long id) {
    return service.getAnimals(id);
  }

  @Override
  @GetMapping
  public List<Guardian> findAll() {
    return service.findAll();
  }

  @Override
  @GetMapping("/{id}")
  public Guardian findById(@PathVariable Long id) {
    return service.findById(id);
  }

  @Override
  @PatchMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody GuardianDto object) {
    service.update(id, object);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
