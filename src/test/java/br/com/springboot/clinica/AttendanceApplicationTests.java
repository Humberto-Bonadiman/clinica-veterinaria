package br.com.springboot.clinica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import br.com.springboot.clinica.entity.Animal;
import br.com.springboot.clinica.entity.Attendance;
import br.com.springboot.clinica.entity.Guardian;
import br.com.springboot.clinica.entity.Veterinary;
import br.com.springboot.clinica.repository.AnimalRepository;
import br.com.springboot.clinica.repository.AttendanceRepository;
import br.com.springboot.clinica.repository.GuardianRepository;
import br.com.springboot.clinica.repository.VeterinaryRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AttendanceApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AnimalRepository animalRepository;

  @Autowired
  private VeterinaryRepository veterinaryRepository;

  @Autowired
  private GuardianRepository guardianRepository;

  @Autowired
  private AttendanceRepository attendanceRepository;

  @BeforeEach
  public void setup() {
    guardianRepository.deleteAll();
    veterinaryRepository.deleteAll();
    animalRepository.deleteAll();
    attendanceRepository.deleteAll();
  }

  @Test
  @Order(1)
  @DisplayName("1 - Should return an error when fetching an attendance that does not exist.")
  void findAnimalThatDoesNotExist() throws Exception {
    mockMvc.perform(get("/attendance/1500"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No attendance found with id: 1500"));
  }

  @Test
  @Order(2)
  @DisplayName("2 - Must register an attendance successfully")
  void registerAttendanceSuccessfully() throws Exception {
    final Guardian guardian = new Guardian();
    guardian.setName("Leonardo Gomes");
    guardian.setAddress("Rua Sorocaba, bairro Aparecida");
    guardianRepository.save(guardian);

    Veterinary veterinary = new Veterinary();
    veterinary.setName("Lucas Costa e Silva");
    veterinaryRepository.save(veterinary);

    Animal animal = new Animal();
    animal.setGuardian(guardian);
    animal.setName("Mayke");
    animal.setSpecies("canine");
    animal.setRace("SRD");
    animal.setBirthDate("17/06/2021");
    animalRepository.save(animal);

    final HashMap<String, Object> body = new HashMap<>();
    body.put("veterinaryId", veterinary.getId());
    body.put("animalId", animal.getId());
    ArrayList<String> symptoms = new ArrayList<String>();
    symptoms.add("cough");
    symptoms.add("tiredness");
    body.put("reasonAttendance", symptoms);

    mockMvc.perform(post("/attendance")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(body)))
            .andExpect(status().isCreated());
  }

  @Test
  @Order(3)
  @DisplayName("3 - Should return Empty list when there is no attendance registered.")
  void emptyListAnimals() throws Exception {
    mockMvc.perform(get("/attendance"))
        .andExpect(status().isOk())
        .andExpect(content().string("[]"));
  }

  @Test
  @Order(4)
  @DisplayName("4 - Should return a list of all registered attendances")
  void listAttendances() throws Exception {
    final Guardian guardian = new Guardian();
    guardian.setName("Leonardo Gomes");
    guardian.setAddress("Rua Sorocaba, bairro Aparecida");
    guardianRepository.save(guardian);

    Veterinary veterinary = new Veterinary();
    veterinary.setName("Lucas Costa e Silva");
    veterinaryRepository.save(veterinary);

    Animal animal = new Animal();
    animal.setGuardian(guardian);
    animal.setName("Mayke");
    animal.setSpecies("canine");
    animal.setRace("SRD");
    animal.setBirthDate("17/06/2021");
    animalRepository.save(animal);

    Attendance attendance1 = new Attendance();
    attendance1.setVeterinary(veterinary);
    attendance1.setAnimal(animal);
    List<String> symptoms1 = Arrays.asList("cough", "tiredness");
    attendance1.setReasonAttendance(symptoms1);
    attendanceRepository.save(attendance1);

    Attendance attendance2 = new Attendance();
    attendance2.setVeterinary(veterinary);
    attendance2.setAnimal(animal);
    List<String> symptoms2 = Arrays.asList("diarrhea");
    attendance2.setReasonAttendance(symptoms2);
    attendanceRepository.save(attendance2);

    mockMvc.perform(get("/attendance"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].reasonAttendance").value(attendance1.getReasonAttendance()))
        .andExpect(jsonPath("$[1].reasonAttendance").value(attendance2.getReasonAttendance()));
  }
}
