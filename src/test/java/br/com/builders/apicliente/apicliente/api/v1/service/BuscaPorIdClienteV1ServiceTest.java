package br.com.builders.apicliente.apicliente.api.v1.service;

import br.com.builders.apicliente.apicliente.api.v1.converter.ClienteConverter;
import br.com.builders.apicliente.apicliente.api.v1.model.ClienteV1Response;
import br.com.builders.apicliente.apicliente.model.Cliente;
import br.com.builders.apicliente.apicliente.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscaPorIdClienteV1ServiceTest {
    @InjectMocks
    private ClienteV1Service clienteV1Service;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClienteConverter clienteConverter;

    @Test
    void quandoClienteEncontradoSucesso() {
        var clienteEsperado = new Cliente("nome aleatório", "endereco aleatório",
                LocalDate.of(1984, 4, 10), "12345678901");

        var clienteResponseEsperado = ClienteV1Response
                .builder()
                .cpf(clienteEsperado.getCpf())
                .nome(clienteEsperado.getNome())
                .endereco(clienteEsperado.getEndereco())
                .dataCadastro(clienteEsperado.getDataCadastro())
                .idade(clienteEsperado.getIdade())
                .build();

        when(this.clienteRepository.getClienteById(clienteResponseEsperado.getId())).thenReturn(Optional.of(clienteEsperado));
        when(this.clienteConverter.clienteParaResponse(any())).thenReturn(clienteResponseEsperado);

        var serviceResponse = this.clienteV1Service.buscarClientePorId(clienteResponseEsperado.getId());

        assertEquals(clienteResponseEsperado.getNome(), serviceResponse.getNome());
        assertEquals(clienteResponseEsperado.getCpf(), serviceResponse.getCpf());
        assertEquals(clienteResponseEsperado.getEndereco(), serviceResponse.getEndereco());
        assertEquals(clienteResponseEsperado.getDataCadastro(), serviceResponse.getDataCadastro());
        assertEquals(clienteResponseEsperado.getIdade(), serviceResponse.getIdade());
    }

    @Test
    void quandoClienteNaoEncontrado() {
        when(this.clienteRepository.getClienteById(anyLong())).thenReturn(Optional.empty());
        var response = this.clienteV1Service.buscarClientePorId(1L);
        assertNull(response);
    }
}
