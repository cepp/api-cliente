package br.com.builders.apicliente.apicliente.api.v1.controller.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class BuscarPaginadoClienteV1ControllerITest {

    @Resource
    private MockMvc mvc;
    @Resource
    private ObjectMapper objectMapper;

    @Test
    void quandoClienteEncontradoSucesso() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"49897572031\",\n" +
                "    \"dataNascimento\": \"2017-08-14\",\n" +
                "    \"endereco\": \"Rua movimentada agora, 344, AP 2314\",\n" +
                "    \"nome\": \"José manoel\"\n" +
                "}";


        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("José manoel"))
                .andExpect(jsonPath("$.cpf").value("49897572031"))
                .andExpect(jsonPath("$.endereco").value("Rua movimentada agora, 344, AP 2314"))
                .andExpect(jsonPath("$.id").exists());


        this.mvc.perform(get(URI.create("/api/v1/cliente"))
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].nome").value("José manoel"))
                .andExpect(jsonPath("$.content[0].cpf").value("49897572031"))
                .andExpect(jsonPath("$.content[0].endereco").value("Rua movimentada agora, 344, AP 2314"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.pageable.pageSize").value(10))
                .andExpect(jsonPath("$.content[0].id").exists());
    }

    @Test
    void quandoClienteNaoEncontrado() throws Exception {
        this.mvc.perform(get(URI.create("/api/v1/cliente"))
                        .queryParam("page", "0")
                        .queryParam("size", "50")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(50))
                .andExpect(jsonPath("$.content").isEmpty());
    }
}
