package br.com.builders.apicliente.apicliente.api.v1.service;

import br.com.builders.apicliente.apicliente.api.v1.converter.ClienteConverter;
import br.com.builders.apicliente.apicliente.api.v1.model.AtualizarClienteV1Request;
import br.com.builders.apicliente.apicliente.api.v1.model.ClienteV1Response;
import br.com.builders.apicliente.apicliente.exception.ClienteNaoExisteException;
import br.com.builders.apicliente.apicliente.model.Cliente;
import br.com.builders.apicliente.apicliente.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizarClienteV1ServiceTest {
    @InjectMocks
    private ClienteV1Service clienteV1Service;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClienteConverter clienteConverter;

    @Test
    void quandoClienteCadastradoSucesso() {
        var clienteRequest = AtualizarClienteV1Request.builder().cpf("12345678901")
                .dataNascimento(LocalDate.of(1984, 4, 10)).endereco("endereço aleatório")
                .nome("nome aleatório").build();

        var clienteEsperado = new Cliente(clienteRequest.getNome(), clienteRequest.getEndereco(),
                clienteRequest.getDataNascimento(), clienteRequest.getCpf());

        var clienteResponseEsperado = ClienteV1Response
                .builder()
                .cpf(clienteEsperado.getCpf())
                .nome(clienteEsperado.getNome())
                .endereco(clienteEsperado.getEndereco())
                .dataCadastro(clienteEsperado.getDataCadastro())
                .idade(clienteEsperado.getIdade())
                .build();

        when(this.clienteRepository.getClienteById(1L)).thenReturn(Optional.of(clienteEsperado));
        when(this.clienteConverter.clienteParaResponse(any())).thenReturn(clienteResponseEsperado);
        when(this.clienteConverter.atualiarRequestParaCliente(clienteRequest, clienteEsperado)).thenReturn(clienteEsperado);
        when(this.clienteRepository.save(any())).thenReturn(clienteEsperado);

        var serviceResponse = this.clienteV1Service.atualizarCliente(1L, clienteRequest);

        assertEquals(clienteResponseEsperado.getNome(), serviceResponse.getNome());
        assertEquals(clienteResponseEsperado.getCpf(), serviceResponse.getCpf());
        assertEquals(clienteResponseEsperado.getEndereco(), serviceResponse.getEndereco());
        assertEquals(clienteResponseEsperado.getDataCadastro(), serviceResponse.getDataCadastro());
        assertEquals(clienteResponseEsperado.getIdade(), serviceResponse.getIdade());
    }

    @Test
    void quandoClienteJaFoiCadastrado() {
        var clienteRequest = AtualizarClienteV1Request.builder().cpf("12345678901")
                .dataNascimento(LocalDate.of(1984, 4, 10)).endereco("endereço aleatório")
                .nome("nome aleatório").build();
        when(this.clienteRepository.getClienteById(1L)).thenReturn(Optional.empty());
        assertThrows(ClienteNaoExisteException.class, () -> this.clienteV1Service.atualizarCliente(1L, clienteRequest));
    }
}
