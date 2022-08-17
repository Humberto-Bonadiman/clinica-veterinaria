package br.com.springboot.clinica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

}
