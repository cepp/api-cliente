package br.com.builders.apicliente.apicliente.api.v1.converter;

import br.com.builders.apicliente.apicliente.api.v1.model.CriarClienteV1Request;
import br.com.builders.apicliente.apicliente.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ClienteConverterTest {
    @InjectMocks
    private ClienteConverter clienteConverter;

    @Test
    void quandoRequestConvertidoParaModel() {
        var criarClienteRequest = CriarClienteV1Request.builder().cpf("12345678901")
                .dataNascimento(LocalDate.of(1984, 4, 10)).endereco("endereço aleatório")
                .nome("nome aleatório").build();

        var clienteConvertido = this.clienteConverter.criarRequestParaCliente(criarClienteRequest);

        assertEquals(criarClienteRequest.getNome(), clienteConvertido.getNome());
        assertEquals(criarClienteRequest.getCpf(), clienteConvertido.getCpf());
        assertEquals(criarClienteRequest.getEndereco(), clienteConvertido.getEndereco());
        assertEquals(criarClienteRequest.getDataNascimento(), clienteConvertido.getDataNascimento());
        assertEquals(37, clienteConvertido.getIdade());
    }

    @Test
    void quandoClienteConvertidoParaResponse() {
        var clienteEsperado = new Cliente("nome aleatorio", "endereco aleatorio",
                LocalDate.of(1984, 4, 10), "12345678901");

        var clienteResponse = this.clienteConverter.clienteParaResponse(clienteEsperado);

        assertEquals(clienteEsperado.getNome(), clienteResponse.getNome());
        assertEquals(clienteEsperado.getCpf(), clienteResponse.getCpf());
        assertEquals(clienteEsperado.getEndereco(), clienteResponse.getEndereco());
        assertEquals(clienteResponse.getDataCadastro(), clienteResponse.getDataCadastro());
        assertEquals(clienteEsperado.getIdade(), clienteResponse.getIdade());
    }
}
