package br.com.builders.apicliente.apicliente.api.v1.controller.it;

import org.apache.commons.lang3.StringUtils;
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

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = BEFORE_CLASS)
class BuscarPorFiltrosClienteV1ControllerITest {

    @Resource
    private MockMvc mvc;

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

        this.mvc.perform(get(URI.create("/api/v1/cliente/filtrado"))
                        .queryParam("nome", "José")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nome").value("José manoel"))
                .andExpect(jsonPath("$[0].cpf").value("49897572031"))
                .andExpect(jsonPath("$[0].endereco").value("Rua movimentada agora, 344, AP 2314"));
    }

    @Test
    void quandoClienteNaoEncontrado() throws Exception {
        this.mvc.perform(get(URI.create("/api/v1/cliente/filtrado"))
                        .queryParam("nome", "Bla Bla")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void quandoFiltrosNaoSaoPassados() throws Exception {
        this.mvc.perform(get(URI.create("/api/v1/cliente/filtrado"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(result -> StringUtils.isBlank(result.toString()));
    }

    @Test
    void quandoNomeVazioSemDataCadastro() throws Exception {
        this.mvc.perform(get(URI.create("/api/v1/cliente/filtrado"))
                        .queryParam("nome", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(result -> StringUtils.isBlank(result.toString()));
    }
}
