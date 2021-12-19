package br.com.builders.apicliente.apicliente.api.v1.controller.it;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = BEFORE_CLASS)
class CriarClienteV1ControllerITest {

    @Resource
    private MockMvc mvc;

    @Test
    void quandoClienteCadastradoSucesso() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"65972975041\",\n" +
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
                .andExpect(jsonPath("$.cpf").value("65972975041"))
                .andExpect(jsonPath("$.endereco").value("Rua movimentada agora, 344, AP 2314"))
                .andExpect(jsonPath("$.idade").exists());
    }

    @Test
    void quandoJaExisteClienteComCpf() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"58818100076\",\n" +
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
                .andExpect(jsonPath("$.cpf").value("58818100076"))
                .andExpect(jsonPath("$.endereco").value("Rua movimentada agora, 344, AP 2314"))
                .andExpect(jsonPath("$.idade").exists());

        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect("Parâmetros passados no request inválidos."::equals);
    }

    @Test
    void quandoCpfInvalido() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"22972975041\",\n" +
                "    \"dataNascimento\": \"2017-08-14\",\n" +
                "    \"endereco\": \"Rua movimentada agora, 344, AP 2314\",\n" +
                "    \"nome\": \"José manoel\"\n" +
                "}";


        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect("Parâmetros passados no request inválidos."::equals);
    }

    @Test
    void quandoCpfObrigatorio() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"\",\n" +
                "    \"dataNascimento\": \"2017-08-14\",\n" +
                "    \"endereco\": \"Rua movimentada agora, 344, AP 2314\",\n" +
                "    \"nome\": \"José manoel\"\n" +
                "}";


        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect("Parâmetros passados no request inválidos."::equals);
    }

    @Test
    void quandoNomeInvalidoMenor() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"65972975041\",\n" +
                "    \"dataNascimento\": \"2017-08-14\",\n" +
                "    \"endereco\": \"Rua movimentada agora, 344, AP 2314\",\n" +
                "    \"nome\": \"aaaaaa\"\n" +
                "}";


        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect("Parâmetros passados no request inválidos."::equals);
    }

    @Test
    void quandoNomeInvalidoMaior() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"65972975041\",\n" +
                "    \"dataNascimento\": \"2017-08-14\",\n" +
                "    \"endereco\": \"Rua movimentada agora, 344, AP 2314\",\n" +
                "    \"nome\": \"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"\n" +
                "}";


        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect("Parâmetros passados no request inválidos."::equals);
    }

    @Test
    void quandoNomeObrigatorio() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"65972975041\",\n" +
                "    \"dataNascimento\": \"2017-08-14\",\n" +
                "    \"endereco\": \"Rua movimentada agora, 344, AP 2314\",\n" +
                "    \"nome\": \"\"\n" +
                "}";


        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect("Parâmetros passados no request inválidos."::equals);
    }

    @Test
    void quandoEnderecoInvalidoMenor() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"65972975041\",\n" +
                "    \"dataNascimento\": \"2017-08-14\",\n" +
                "    \"endereco\": \"Rua mov, AP 2314\",\n" +
                "    \"nome\": \"José manoel\"\n" +
                "}";


        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect("Parâmetros passados no request inválidos."::equals);
    }

    @Test
    void quandoEnderecoInvalidoMaior() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"65972975041\",\n" +
                "    \"dataNascimento\": \"2017-08-14\",\n" +
                "    \"endereco\": \"Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314Rua movimentada agora, 344, AP 2314\",\n" +
                "    \"nome\": \"José manoel\"\n" +
                "}";


        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect("Parâmetros passados no request inválidos."::equals);
    }

    @Test
    void quandoEnderecoObrigatorio() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"65972975041\",\n" +
                "    \"dataNascimento\": \"2017-08-14\",\n" +
                "    \"endereco\": \"\",\n" +
                "    \"nome\": \"José manoel\"\n" +
                "}";


        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect("Parâmetros passados no request inválidos."::equals);
    }

    @Test
    void quandoNascimentoObrigatorio() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"65972975041\",\n" +
                "    \"dataNascimento\": \"\",\n" +
                "    \"endereco\": \"\",\n" +
                "    \"nome\": \"José manoel\"\n" +
                "}";


        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect("Parâmetros passados no request inválidos."::equals);
    }

    @Test
    void quandoNascimentoInvalido() throws Exception {
        var jsonRequest = "{\n" +
                "    \"cpf\": \"65972975041\",\n" +
                "    \"dataNascimento\": \"10/02/1999\",\n" +
                "    \"endereco\": \"\",\n" +
                "    \"nome\": \"José manoel\"\n" +
                "}";


        this.mvc.perform(post(URI.create("/api/v1/cliente"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect("Parâmetros passados no request inválidos."::equals);
    }
}
