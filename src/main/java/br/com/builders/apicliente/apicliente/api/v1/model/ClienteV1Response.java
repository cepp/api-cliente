package br.com.builders.apicliente.apicliente.api.v1.model;

import br.com.builders.apicliente.apicliente.api.v1.doc.ClienteV1ResponseDoc;
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
public class ClienteV1Response implements ClienteV1ResponseDoc {
    Long id;
    String nome;
    String endereco;
    String cpf;
    LocalDate dataCadastro;
    Integer idade;
}
