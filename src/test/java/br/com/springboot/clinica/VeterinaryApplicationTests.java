package br.com.springboot.clinica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import br.com.springboot.clinica.entity.Veterinary;
import br.com.springboot.clinica.repository.VeterinaryRepository;
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
class VeterinaryApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private VeterinaryRepository veterinaryRepository;

  @BeforeEach
  public void setup() {
    veterinaryRepository.deleteAll();
  }

  @Test
  @Order(1)
  @DisplayName("1 -  Must successfully add a vet to the database.")
  void addVetSuccessfully() throws Exception {
    final Veterinary veterinary = new Veterinary();
    veterinary.setName("Maurício Costa e Silva");

    mockMvc.perform(post("/veterinary")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(veterinary)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  @Order(2)
  @DisplayName("2 - Should return an error when searching for a vet that does not exist.")
  void searchVetThatDoesNotExist() throws Exception {
    mockMvc.perform(get("/veterinary/10"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No veterinarian found with id: 10"));
  }

  @Test
  @Order(3)
  @DisplayName("3 - Must successfully delete a vet")
  void deleteVetSuccessfully() throws Exception {
    Veterinary veterinary = new Veterinary();
    veterinary.setName("Lucas Costa e Silva");
    veterinaryRepository.save(veterinary);
    mockMvc.perform(delete("/veterinary/" + veterinary.getId()))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(4)
  @DisplayName("4 - Should return an error when deleting a vet that doesn't exist.")
  void deleteVetThatDoesNotExist() throws Exception {
    mockMvc.perform(delete("/veterinary/15"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("No veterinarian found with id: 15"));
  }

  @Test
  @Order(5)
  @DisplayName("5 - Must change a vet successfully.")
  void updateVetSuccessfully() throws Exception {
    Veterinary veterinary = new Veterinary();
    veterinary.setName("Lucas Costa e Silva");
    veterinaryRepository.save(veterinary);
    veterinary.setName("Lucas Costa Silva");
    mockMvc.perform(patch("/veterinary/" + veterinary.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(veterinary)))
        .andExpect(status().isNoContent());
  }

  @Test
  @Order(6)
  @DisplayName("6 - Should return Empty list when there is no registered veterinarian.")
  void emptyListVets() throws Exception {
    mockMvc.perform(get("/veterinary"))
        .andExpect(status().isOk())
        .andExpect(content().string("[]"));
  }

  @Test
  @Order(7)
  @DisplayName("7 - Should return a list of all registered veterinarians")
  void listAllVets() throws Exception {
    Veterinary veterinary1 = new Veterinary();
    veterinary1.setName("Lucas Costa e Silva");
    veterinaryRepository.save(veterinary1);
    Veterinary veterinary2 = new Veterinary();
    veterinary2.setName("Guilherme Azevedo");
    veterinaryRepository.save(veterinary2);
    mockMvc.perform(get("/veterinary"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value(veterinary1.getName()))
        .andExpect(jsonPath("$[1].name").value(veterinary2.getName()));
  }

  @Test
  @Order(8)
  @DisplayName("8 - Should return the vet according to the id informed")
  void findSpecificVet() throws Exception {
    Veterinary veterinary = new Veterinary();
    veterinary.setName("Mariana Chagas Silva");
    veterinaryRepository.save(veterinary);
    mockMvc.perform(get("/veterinary/" + veterinary.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(veterinary.getName()));
  }
}
