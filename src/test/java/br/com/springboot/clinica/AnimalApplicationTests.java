package br.com.springboot.clinica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

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
import br.com.springboot.clinica.entity.Guardian;
import br.com.springboot.clinica.repository.AnimalRepository;
import br.com.springboot.clinica.repository.GuardianRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimalApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AnimalRepository animalRepository;

  @Autowired
  private GuardianRepository guardianRepository;

  @BeforeEach
  public void setup() {
    guardianRepository.deleteAll();
    animalRepository.deleteAll();
  }

  @Test
  @Order(1)
  @DisplayName("1 - Should return an error when fetching an animal that does not exist.")
  void findAnimalThatDoesNotExist() throws Exception {
    mockMvc.perform(get("/animal/1500"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No animal found with id: 1500"));
  }

  @Test
  @Order(2)
  @DisplayName("2 - Must register an animal successfully")
  void registerAnimalSuccessfully() throws Exception {
    final Guardian guardian = new Guardian();
    guardian.setName("Leonardo Gomes");
    guardian.setAddress("Rua Sorocaba, bairro Aparecida");
    guardianRepository.save(guardian);

    final HashMap<String, String> body = new HashMap<>();
    body.put("guardianId", guardian.getId().toString());
    body.put("name", "Mayke");
    body.put("species", "canine");
    body.put("race", "SRD");
    body.put("birthDate", "17/10/2010");

    mockMvc.perform(post("/animal")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(body)))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(3)
  @DisplayName("3 - Should return Empty list when there is no animal registered.")
  void emptyListAnimals() throws Exception {
    mockMvc.perform(get("/animal"))
        .andExpect(status().isOk())
        .andExpect(content().string("[]"));
  }

  @Test
  @Order(4)
  @DisplayName("4 - Should return a list of all registered animals")
  void listAllAnimals() throws Exception {
    final Guardian guardian = new Guardian();
    guardian.setName("Leonardo Gomes");
    guardian.setAddress("Rua Sorocaba, bairro Aparecida");
    guardianRepository.save(guardian);

    Animal body1 = new Animal();
    body1.setGuardian(guardian);
    body1.setName("Mayke");
    body1.setSpecies("canine");
    body1.setRace("SRD");
    body1.setBirthDate("17/10/2010");
    animalRepository.save(body1);

    Animal body2 = new Animal();
    body2.setGuardian(guardian);
    body2.setName("Peter");
    body2.setSpecies("feline");
    body2.setRace("SRD");
    body2.setBirthDate("20/05/2020");
    animalRepository.save(body2);
    System.out.println(jsonPath("$[0].guardian"));
    mockMvc.perform(get("/animal"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].name").value(body1.getName()))
      .andExpect(jsonPath("$[0].species").value(body1.getSpecies()))
      .andExpect(jsonPath("$[0].race").value(body1.getRace()))
      .andExpect(jsonPath("$[0].birthDate").value(body1.getBirthDate()))
      .andExpect(jsonPath("$[1].name").value(body2.getName()))
      .andExpect(jsonPath("$[1].species").value(body2.getSpecies()))
      .andExpect(jsonPath("$[1].race").value(body2.getRace()))
      .andExpect(jsonPath("$[1].birthDate").value(body2.getBirthDate()));
  }

  @Test
  @Order(5)
  @DisplayName("5 - Must change a registered animal")
  void alterAnimal() throws Exception {
    final Guardian guardian1 = new Guardian();
    guardian1.setName("Leonardo Gomes");
    guardian1.setAddress("Rua Sorocaba, bairro Aparecida");
    guardianRepository.save(guardian1);

    final Guardian guardian2 = new Guardian();
    guardian2.setName("Maria Eduarda");
    guardian2.setAddress("Rua Americana, bairro Aparecida");
    guardianRepository.save(guardian2);

    Animal animal = new Animal();
    animal.setGuardian(guardian1);
    animal.setName("Mayke");
    animal.setSpecies("canine");
    animal.setRace("SRD");
    animal.setBirthDate("17/06/2022");
    animalRepository.save(animal);

    final HashMap<String, Object> body = new HashMap<>();
    body.put("name", "Hulk");
    body.put("guardianId", guardian2.getId());
    body.put("species", "canine");
    body.put("race", "SRD");
    body.put("birthDate", "17/06/2022");

    mockMvc.perform(patch("/animal/" + animal.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(body)))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(6)
  @DisplayName("6 - Must successfully delete an animal")
  void deleteAnimal() throws Exception {
    final Guardian guardian = new Guardian();
    guardian.setName("Leonardo Gomes");
    guardian.setAddress("Rua Sorocaba, bairro Aparecida");
    guardianRepository.save(guardian);

    Animal animal = new Animal();
    animal.setGuardian(guardian);
    animal.setName("Mayke");
    animal.setSpecies("canine");
    animal.setRace("SRD");
    animal.setBirthDate("17/06/2022");
    animalRepository.save(animal);

    mockMvc.perform(delete("/animal/" + animal.getId()))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(7)
  @DisplayName("7 - Should return an error when deleting a animal that doesn't exist.")
  void deleteGuardianThatDoesNotExist() throws Exception {
    mockMvc.perform(delete("/animal/15"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No animal found with id: 15"));
  }

  @Test
  @Order(8)
  @DisplayName("8 - Should return the animal according to the id informed")
  void findSpecificAnimal() throws Exception {
    final Guardian guardian = new Guardian();
    guardian.setName("Leonardo Gomes");
    guardian.setAddress("Rua Sorocaba, bairro Aparecida");
    guardianRepository.save(guardian);

    Animal animal = new Animal();
    animal.setGuardian(guardian);
    animal.setName("Mayke");
    animal.setSpecies("canine");
    animal.setRace("SRD");
    animal.setBirthDate("17/06/2022");
    animalRepository.save(animal);

    mockMvc.perform(get("/animal/" + animal.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(animal.getName()))
        .andExpect(jsonPath("$.species").value(animal.getSpecies()))
        .andExpect(jsonPath("$.race").value(animal.getRace()))
        .andExpect(jsonPath("$.birthDate").value(animal.getBirthDate()));
  }
}
