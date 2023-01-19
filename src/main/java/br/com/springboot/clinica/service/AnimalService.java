package br.com.springboot.clinica.service;

import br.com.springboot.clinica.dto.AnimalDto;
import br.com.springboot.clinica.entity.Animal;
import br.com.springboot.clinica.entity.Attendance;
import br.com.springboot.clinica.entity.Guardian;
import br.com.springboot.clinica.exception.AnimalNotFoundException;
import br.com.springboot.clinica.exception.GuardianNotFoundException;
import br.com.springboot.clinica.repository.AnimalRepository;
import br.com.springboot.clinica.repository.GuardianRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AnimalService implements ServiceInterface<AnimalDto, Animal> {

  @Autowired
  AnimalRepository repository;

  @Autowired
  GuardianRepository guardianRepository;

  @Override
  public Animal create(AnimalDto object) {
    Assert.notNull(object.getName(), "Name cannot be blank");
    try {
      Animal newAnimal = new Animal(
          object.getName(),
          object.getSpecies(),
          object.getRace(),
          object.getBirthDate()
      );
      Guardian guardian = guardianRepository.findById(object.getGuardianId()).get();
      guardian.addAnimals(newAnimal);
      Guardian saved = guardianRepository.save(guardian);
      return saved.getAnimals().get(saved.getAnimals().size() - 1);
    } catch (Exception e) {
      throw new GuardianNotFoundException(object.getGuardianId().toString());
    }
  }

  @Override
  public Animal findById(Long id) {
    try {
      return repository.findById(id).get();
    } catch (Exception e) {
      throw new AnimalNotFoundException(id.toString());
    }
  }

  @Override
  public List<Animal> findAll() {
    return repository.findAll();
  }

  @Override
  public void update(Long id, AnimalDto object) {
    try {
      Animal updateAnimal = repository.findById(id).get();
      updateAnimal.setName(object.getName());
      updateAnimal.setSpecies(object.getSpecies());
      updateAnimal.setRace(object.getRace());
      updateAnimal.setBirthDate(object.getBirthDate());
    } catch (Exception e) {
      throw new AnimalNotFoundException(id.toString());
    }
  }

  /**
   * delete animal.
   */
  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (Exception e) {
      throw new AnimalNotFoundException(id.toString());
    }
  }

  /**
   * get attendance.
   */
  public List<Attendance> getAttendance(Long id) {
    try {
      Animal animal =  repository.findById(id).get();
      return animal.getAttendance();
    } catch (Exception e) {
      throw new AnimalNotFoundException(id.toString());
    }
  }


}
