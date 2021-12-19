package br.com.builders.apicliente.apicliente.api.v1.doc;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Respresentação da resposta de cliente")
public interface ClienteV1ResponseDoc {
    @Schema(description = "Identificador do cadastrado do cliente", example = "1")
    Long getId();
    @Schema(description = "Nome cadastrado do cliente", example = "Jão Manoel")
    String getNome();
    @Schema(description = "Endereço cadastrado do cliente", example = "Rua movimentada, 344, AP 2314")
    String getEndereco();
    @Schema(description = "Idade do cliente", example = "66")
    Integer getIdade();
    @Schema(description = "Data em que o cliente realizou o cadastro", type = "string", format = "date", example = "2021-12-18")
    LocalDate getDataCadastro();
    @Schema(description = "Número do CPF do cliente", type = "string", minLength = 11, maxLength = 11, example = "65972975041")
    String getCpf();
}
