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
public class AttendanceService {

  @Autowired
  AttendanceRepository repository;

  @Autowired
  AnimalRepository animalRepository;

  @Autowired
  VeterinaryRepository veterinaryRepository;

  /**
   * create attendance.
   */
  public synchronized Attendance create(AttendanceDto object) {
    try {
      Attendance newAttendance = new Attendance();
      newAttendance.setReasonAttendance(object.getReasonAttendance());
      Veterinary veterinary = veterinaryRepository.findById(object.getVeterinaryId()).get();
      veterinary.addAttendance(newAttendance);
      Animal animal = animalRepository.findById(object.getAnimalId()).get();
      animal.addAttendance(newAttendance);
      return repository.save(newAttendance);
    } catch (Exception e) {
      throw new AnimalNotFoundException(object.getAnimalId().toString());
    }
  }

  /**
   * find attendance by id.
   */
  public Attendance findById(Long id) {
    try {
      return repository.findById(id).get();
    } catch (Exception e) {
      throw new AttendanceNotFoundException(id.toString());
    }
  }

  public List<Attendance> findAll() {
    return repository.findAll();
  }

  /**
   * update Attendance.
   */
  public void update(Long id, AttendanceDto object) {
    try {
      Attendance updateAttendance = repository.findById(id).get();
      updateAttendance.setReasonAttendance(object.getReasonAttendance());
    } catch (Exception e) {
      throw new AttendanceNotFoundException(id.toString());
    }
  }
}
