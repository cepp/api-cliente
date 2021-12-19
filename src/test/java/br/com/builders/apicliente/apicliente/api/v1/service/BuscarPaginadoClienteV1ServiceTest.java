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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPaginadoClienteV1ServiceTest {
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

        var pageable = PageRequest.of(1, 20);
        var page = new PageImpl<>(List.of(clienteEsperado), pageable, 1);
        var pageResponse = new PageImpl<>(List.of(clienteResponseEsperado), pageable, 1);

        when(this.clienteRepository.findAll(pageable)).thenReturn(page);
        when(this.clienteConverter.clienteParaResponse(any())).thenReturn(clienteResponseEsperado);

        var serviceResponse = this.clienteV1Service.buscarClientePaginado(pageable);

        assertNotNull(serviceResponse);
        assertFalse(serviceResponse.isEmpty());
        assertEquals(pageResponse, serviceResponse);
    }
}
