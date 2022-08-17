package br.com.springboot.clinica.service;

import br.com.springboot.clinica.dto.AttendanceDto;
import br.com.springboot.clinica.entity.Animal;
import br.com.springboot.clinica.entity.Attendance;
import br.com.springboot.clinica.entity.Veterinary;
import br.com.springboot.clinica.exception.AnimalNotFoundException;
import br.com.springboot.clinica.exception.AttendanceNotFoundException;
import br.com.springboot.clinica.repository.AnimalRepository;
import br.com.springboot.clinica.repository.AttendanceRepository;
import br.com.springboot.clinica.repository.VeterinaryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService implements ServiceInterface<AttendanceDto, Attendance> {

  @Autowired
  AttendanceRepository repository;

  @Autowired
  AnimalRepository animalRepository;

  @Autowired
  VeterinaryRepository veterinaryRepository;

  @Override
  public Attendance create(AttendanceDto object) {
    try {
      Animal animal = animalRepository.findById(object.getAnimalId()).get();
      Veterinary veterinary = veterinaryRepository.findById(object.getVeterinaryId()).get();
      Attendance newAttendance = new Attendance();
      newAttendance.setReasonAttendance(object.getReasonAttendance());
      veterinary.addAttendance(newAttendance);
      animal.addAttendance(newAttendance);
      veterinaryRepository.save(veterinary);
      Animal saved = animalRepository.save(animal);
      return saved.getAttendance().get(saved.getAttendance().size() - 1);
    } catch (Exception e) {
      throw new AnimalNotFoundException(object.getAnimalId().toString());
    }
  }

  @Override
  public Attendance findById(Long id) {
    try {
      return repository.findById(id).get();
    } catch (Exception e) {
      throw new AttendanceNotFoundException(id.toString());
    }
  }

  @Override
  public List<Attendance> findAll() {
    return repository.findAll();
  }

  @Override
  public void update(Long id, AttendanceDto object) {
    try {
      Attendance updateAttendance = repository.findById(id).get();
      updateAttendance.setReasonAttendance(object.getReasonAttendance());
    } catch (Exception e) {
      throw new AttendanceNotFoundException(id.toString());
    }
  }

  @Override
  public void delete(Long id) {
    try {
      repository.deleteById(id);
    } catch (Exception e) {
      throw new AttendanceNotFoundException(id.toString());
    }
  }
}
