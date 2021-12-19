package br.com.builders.apicliente.apicliente.api.v1.doc;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Schema(description = "Respresentação da requisição de atualização do cliente")
public interface AtualizarClienteV1RequestDoc {
    @Size(min = 10, max = 150)
    @NotBlank(message = "Nome do cliente é obrigatório")
    @Schema(description = "Nome cadastrado do cliente", minLength = 10, maxLength = 150, required = true, example = "Jão Manoel")
    String getNome();
    @Size(min = 20, max = 255)
    @NotBlank(message = "Endereço do cliente é obrigatório")
    @Schema(description = "Endereço cadastrado do cliente", required = true, minLength = 30, maxLength = 255, example = "Rua movimentada, 344, AP 2314")
    String getEndereco();
    @NotNull(message = "Data de nascimento do cliente é obrigatório")
    @Schema(description = "Data de nascimento do cliente", type = "string", required = true, format = "date", example = "2021-12-18")
    LocalDate getDataNascimento();
    @CPF(message = "CPF inválido")
    @NotBlank(message = "CPF do cliente é obrigatório")
    @Schema(description = "Número do CPF do cliente", type = "string", minLength = 11, maxLength = 11, required = true, example = "65972975041")
    String getCpf();
}
