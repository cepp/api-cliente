package br.com.builders.apicliente.apicliente.api.v1.converter;

import br.com.builders.apicliente.apicliente.api.v1.doc.ClienteV1ResponseDoc;
import br.com.builders.apicliente.apicliente.api.v1.model.AtualizarClienteV1Request;
import br.com.builders.apicliente.apicliente.api.v1.model.ClienteV1Response;
import br.com.builders.apicliente.apicliente.api.v1.model.CriarClienteV1Request;
import br.com.builders.apicliente.apicliente.model.Cliente;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@Component
public class ClienteConverter {
    public Cliente criarRequestParaCliente(@Valid @NotNull CriarClienteV1Request request) {
        return new Cliente(request.getNome(), request.getEndereco(), request.getDataNascimento(), request.getCpf());
    }

    public ClienteV1ResponseDoc clienteParaResponse(@NotNull Cliente cliente) {
        return ClienteV1Response
                .builder()
                .cpf(cliente.getCpf())
                .nome(cliente.getNome())
                .endereco(cliente.getEndereco())
                .dataCadastro(cliente.getDataCadastro())
                .idade(cliente.getIdade())
                .id(cliente.getId())
                .build();
    }


    public Cliente atualiarRequestParaCliente(@Valid @NotNull AtualizarClienteV1Request request, @NotNull Cliente cliente) {
        return cliente.alteraCpf(request.getCpf())
                .alteraEndereco(request.getEndereco())
                .alteraNome(request.getNome())
                .alteraDataNascimento(request.getDataNascimento());
    }
}
