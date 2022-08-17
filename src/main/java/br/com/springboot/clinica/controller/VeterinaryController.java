package br.com.springboot.clinica.controller;

import br.com.springboot.clinica.dto.VeterinaryDto;
import br.com.springboot.clinica.entity.Attendance;
import br.com.springboot.clinica.entity.Veterinary;
import br.com.springboot.clinica.service.VeterinaryService;
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
@RequestMapping("/veterinary")
public class VeterinaryController implements ControllerInterface<VeterinaryDto, Veterinary> {

  @Autowired
  VeterinaryService service;

  @Override
  @PostMapping
  public ResponseEntity<Veterinary> create(@RequestBody VeterinaryDto object) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(object));
  }

  @GetMapping("/{id}/attendance")
  public List<Attendance> getAttendances(@PathVariable Long id) {
    return service.getAttendance(id);
  }

  @Override
  @GetMapping
  public List<Veterinary> findAll() {
    return service.findAll();
  }

  @Override
  @GetMapping("/{id}")
  public Veterinary findById(@PathVariable Long id) {
    return service.findById(id);
  }

  @Override
  @PatchMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody VeterinaryDto object) {
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
