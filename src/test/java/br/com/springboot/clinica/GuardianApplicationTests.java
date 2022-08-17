package br.com.springboot.clinica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import br.com.springboot.clinica.entity.Guardian;
import br.com.springboot.clinica.repository.GuardianRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
public class GuardianApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private GuardianRepository guardianRepository;

  @BeforeEach
  public void setup() {
    guardianRepository.deleteAll();
  }

  @Test
  @Order(1)
  @DisplayName("1 -  Must successfully add a guardian to the database.")
  void addGuardianSuccessfully() throws Exception {
    final Guardian guardian = new Guardian();
    guardian.setName("Maurício Costa e Silva");

    mockMvc.perform(post("/guardian")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(guardian)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(2)
  @DisplayName("2 - Should return an error when searching for a guardian that does not exist.")
  void searchGuardianThatDoesNotExist() throws Exception {
    mockMvc.perform(get("/guardian/10"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No guardian found with id: 10"));
  }

  @Test
  @Order(3)
  @DisplayName("3 - Must successfully delete a guardian")
  void deleteGuardianSuccessfully() throws Exception {
    Guardian guardian = new Guardian();
    guardian.setName("Luiza Albuquerque");
    guardianRepository.save(guardian);
    mockMvc.perform(delete("/guardian/" + guardian.getId()))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(4)
  @DisplayName("4 - Should return an error when deleting a guardian that doesn't exist.")
  void deleteGuardianThatDoesNotExist() throws Exception {
    mockMvc.perform(delete("/guardian/15"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No guardian found with id: 15"));
  }

  @Test
  @Order(5)
  @DisplayName("5 - Must change a guardian successfully.")
  void updateGuardianSuccessfully() throws Exception {
    Guardian guardian = new Guardian();
    guardian.setName("Júlio Costa e Silva");
    guardianRepository.save(guardian);
    guardian.setName("Júlio Costa Silva");
    mockMvc.perform(patch("/guardian/" + guardian.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(guardian)))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(6)
  @DisplayName("6 - Should return Empty list when there is no registered guardian.")
  void emptyListGuardian() throws Exception {
    mockMvc.perform(get("/guardian"))
        .andExpect(status().isOk())
        .andExpect(content().string("[]"));
  }

  @Test
  @Order(7)
  @DisplayName("7 - Should return a list of all registered guardians")
  void listAllGuardians() throws Exception {
    Guardian guardian1 = new Guardian();
    guardian1.setName("Lucas Costa e Silva");
    guardianRepository.save(guardian1);
    Guardian guardian2 = new Guardian();
    guardian2.setName("Guilherme Azevedo");
    guardianRepository.save(guardian2);
    mockMvc.perform(get("/guardian"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value(guardian1.getName()))
        .andExpect(jsonPath("$[1].name").value(guardian2.getName()));
  }

  @Test
  @Order(8)
  @DisplayName("8 - Should return the guardian according to the id informed")
  void findSpecificVet() throws Exception {
    Guardian guardian = new Guardian();
    guardian.setName("Mariana Chagas Silva");
    guardianRepository.save(guardian);
    mockMvc.perform(get("/guardian/" + guardian.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(guardian.getName()));
  }
}
