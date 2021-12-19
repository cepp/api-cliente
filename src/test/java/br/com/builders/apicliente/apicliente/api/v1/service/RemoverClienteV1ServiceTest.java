package br.com.builders.apicliente.apicliente.api.v1.service;

import br.com.builders.apicliente.apicliente.api.v1.converter.ClienteConverter;
import br.com.builders.apicliente.apicliente.api.v1.model.ClienteV1Response;
import br.com.builders.apicliente.apicliente.api.v1.model.CriarClienteV1Request;
import br.com.builders.apicliente.apicliente.exception.ClienteExistenteException;
import br.com.builders.apicliente.apicliente.exception.ClienteNaoExisteException;
import br.com.builders.apicliente.apicliente.model.Cliente;
import br.com.builders.apicliente.apicliente.repository.ClienteRepository;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemoverClienteV1ServiceTest {
    @InjectMocks
    private ClienteV1Service clienteV1Service;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClienteConverter clienteConverter;

    @Test
    void quandoClienteRemovidoSucesso() {
        var criarClienteRequest = CriarClienteV1Request.builder().cpf("12345678901")
                .dataNascimento(LocalDate.of(1984, 4, 10)).endereco("endereço aleatório")
                .nome("nome aleatório").build();

        var clienteEsperado = new Cliente(criarClienteRequest.getNome(), criarClienteRequest.getEndereco(),
                criarClienteRequest.getDataNascimento(), criarClienteRequest.getCpf());

        var clienteResponseEsperado = ClienteV1Response
                .builder()
                .cpf(clienteEsperado.getCpf())
                .nome(clienteEsperado.getNome())
                .endereco(clienteEsperado.getEndereco())
                .dataCadastro(clienteEsperado.getDataCadastro())
                .idade(clienteEsperado.getIdade())
                .id(1L)
                .build();

        when(this.clienteRepository.getClienteById(clienteResponseEsperado.getId())).thenReturn(Optional.of(clienteEsperado));
        when(this.clienteConverter.clienteParaResponse(any())).thenReturn(clienteResponseEsperado);
        doNothing().when(this.clienteRepository).delete(clienteEsperado);

        var serviceResponse = this.clienteV1Service.removerCliente(clienteResponseEsperado.getId());

        assertEquals(clienteResponseEsperado.getNome(), serviceResponse.getNome());
        assertEquals(clienteResponseEsperado.getCpf(), serviceResponse.getCpf());
        assertEquals(clienteResponseEsperado.getEndereco(), serviceResponse.getEndereco());
        assertEquals(clienteResponseEsperado.getDataCadastro(), serviceResponse.getDataCadastro());
        assertEquals(clienteResponseEsperado.getIdade(), serviceResponse.getIdade());
    }

    @Test
    void quandoClienteNaoExistente() {
        when(this.clienteRepository.getClienteById(1L)).thenReturn(Optional.empty());
        assertThrows(ClienteNaoExisteException.class, () -> this.clienteV1Service.removerCliente(1L));
    }
}
