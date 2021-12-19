package br.com.builders.apicliente.apicliente.api.v1.model;

import br.com.builders.apicliente.apicliente.api.v1.doc.AtualizarClienteV1RequestDoc;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AtualizarClienteV1Request implements AtualizarClienteV1RequestDoc {
    String nome;
    String cpf;
    String endereco;
    LocalDate dataNascimento;
}
