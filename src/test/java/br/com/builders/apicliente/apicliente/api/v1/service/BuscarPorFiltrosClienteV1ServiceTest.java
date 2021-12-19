package br.com.builders.apicliente.apicliente.api.v1.service;

import br.com.builders.apicliente.apicliente.api.v1.converter.ClienteConverter;
import br.com.builders.apicliente.apicliente.api.v1.model.ClienteV1Response;
import br.com.builders.apicliente.apicliente.exception.RequisicaoInvalidaExeception;
import br.com.builders.apicliente.apicliente.model.Cliente;
import br.com.builders.apicliente.apicliente.repository.ClienteRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPorFiltrosClienteV1ServiceTest {
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

        var clientesResponse = Set.of(clienteResponseEsperado);
        var clientes = Set.of(clienteEsperado);

        when(this.clienteRepository.findClienteByFiltrado(anyString(), any())).thenReturn(clientes);
        when(this.clienteConverter.clienteParaResponse(any())).thenReturn(clienteResponseEsperado);

        var serviceResponse = this.clienteV1Service.buscarClientePorFiltros("clientes", LocalDate.now());

        assertNotNull(serviceResponse);
        assertFalse(serviceResponse.isEmpty());
        assertEquals(clientesResponse, serviceResponse);
    }

    @Test
    void quandoClienteNaoEncontrado() {
        when(this.clienteRepository.findClienteByFiltrado(anyString(), any())).thenReturn(Collections.emptySet());
        var response = this.clienteV1Service.buscarClientePorFiltros("cliente", LocalDate.now());
        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void quandoFiltrosInvalidos() {
        assertThrows(RequisicaoInvalidaExeception.class, () -> this.clienteV1Service.buscarClientePorFiltros(null, null));
        assertThrows(RequisicaoInvalidaExeception.class, () -> this.clienteV1Service.buscarClientePorFiltros("", null));
    }
}
