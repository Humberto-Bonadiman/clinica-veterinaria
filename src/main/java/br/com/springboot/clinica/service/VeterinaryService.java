package br.com.springboot.clinica.service;

import br.com.springboot.clinica.dto.VeterinaryDto;
import br.com.springboot.clinica.entity.Attendance;
import br.com.springboot.clinica.entity.Veterinary;
import br.com.springboot.clinica.exception.VetNotFoundException;
import br.com.springboot.clinica.repository.VeterinaryRepository;
// import br.com.springboot.clinica.service.ServiceInterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class VeterinaryService implements ServiceInterface<VeterinaryDto, Veterinary> {

  @Autowired
  VeterinaryRepository repository;

  @Override
  public Veterinary create(VeterinaryDto object) {
    Assert.notNull(object.getName(), "Name cannot be blank");
    Veterinary newVeterinary = new Veterinary(object.getName());
    return repository.save(newVeterinary);
  }

  @Override
  public List<Veterinary> findAll() {
    return repository.findAll();
  }

  @Override
  public Veterinary findById(Long id) {
    try {
      return repository.findById(id).get();
    } catch (Exception e) {
      throw new VetNotFoundException(id.toString());
    }
  }

  @Override
  public void update(Long id, VeterinaryDto object) {
    try {
      Veterinary veterinary = repository.findById(id).get();
      veterinary.setName(object.getName());
    } catch (Exception e) {
      throw new VetNotFoundException(id.toString());
    }
  }

  /**
   * delete veterinary.
   */
  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (Exception e) {
      throw new VetNotFoundException(id.toString());
    }
  }

  /**
   * get attendance.
   */
  public List<Attendance> getAttendance(Long id) {
    try {
      Veterinary veterinary = repository.findById(id).get();
      return veterinary.getAttendance();
    } catch (Exception e) {
      throw new VetNotFoundException(id.toString());
    }
  }
}
