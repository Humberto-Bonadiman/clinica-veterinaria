package br.com.springboot.clinica.service;

import br.com.springboot.clinica.dto.GuardianDto;
import br.com.springboot.clinica.entity.Animal;
import br.com.springboot.clinica.entity.Guardian;
import br.com.springboot.clinica.exception.GuardianNotFoundException;
import br.com.springboot.clinica.repository.GuardianRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class GuardianService  implements ServiceInterface<GuardianDto, Guardian>{

  @Autowired
  GuardianRepository repository;

  @Override
  public Guardian create(GuardianDto object) {
    Assert.notNull(object.getName(), "Name cannot be blank");
    Guardian newGuardian = new Guardian();
    newGuardian.setName(object.getName());
    newGuardian.setAddress(object.getAddress());
    return repository.save(newGuardian);
  }

  @Override
  public List<Guardian> findAll() {
    return repository.findAll();
  }

  @Override
  public Guardian findById(Long id) {
    try {
      return repository.findById(id).get();
    } catch (Exception e) {
      throw new GuardianNotFoundException(id.toString());
    }
  }

  @Override
  public void update(Long id, GuardianDto object) {
    try {
      Guardian guardian = repository.findById(id).get();
      guardian.setName(object.getName());
      guardian.setAddress(object.getAddress());
      repository.save(guardian);
    } catch (Exception e) {
      throw new GuardianNotFoundException(id.toString());
    }
  }

  @Override
  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (Exception e) {
      throw new GuardianNotFoundException(id.toString());
    }
  }

  /**
   * get Animals.
   */
  public List<Animal> getAnimals(Long id) {
    try {
      Guardian guardian = repository.findById(id).get();
      return guardian.getAnimals();
    } catch (Exception e) {
      throw new GuardianNotFoundException(id.toString());
    }
  }
}
