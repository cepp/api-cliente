package br.com.builders.apicliente.apicliente.api.v1.controller.ut;

import br.com.builders.apicliente.apicliente.api.v1.controller.ClienteV1Controller;
import br.com.builders.apicliente.apicliente.api.v1.doc.ClienteV1ResponseDoc;
import br.com.builders.apicliente.apicliente.api.v1.model.ClienteV1Response;
import br.com.builders.apicliente.apicliente.api.v1.service.ClienteV1Service;
import br.com.builders.apicliente.apicliente.exception.RequisicaoInvalidaExeception;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPorFiltrosClienteV1ControllerTest {
    @InjectMocks
    private ClienteV1Controller clienteV1Controller;
    @Mock
    private ClienteV1Service clienteV1Service;

    @Test
    void quandoClienteEncontradoSucesso() {
        var respostaEsperada = ClienteV1Response.builder().cpf("12345678901")
                .dataCadastro(LocalDate.now()).endereco("endereço aleatório").nome("nome aleatório").idade(11).id(1L).build();
        var respostasEsperadas = new HashSet<ClienteV1ResponseDoc>();
        respostasEsperadas.add(respostaEsperada);
        when(this.clienteV1Service.buscarClientePorFiltros(anyString(), any())).thenReturn(respostasEsperadas);

        var resposta = this.clienteV1Controller.buscarClientesPorFiltro("cliente", LocalDate.now());

        assertNotNull(resposta);
        assertFalse(resposta.isEmpty());
        assertEquals(respostasEsperadas, resposta);
    }

    @Test
    void quandoClienteNaoCadastrado() {
        when(this.clienteV1Service.buscarClientePorFiltros(anyString(), any())).thenReturn(Collections.emptySet());
        var response = this.clienteV1Controller.buscarClientesPorFiltro("cliente", LocalDate.now());
        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void quandoParametrosNulos() {
        when(this.clienteV1Service.buscarClientePorFiltros(null, null)).thenThrow(RequisicaoInvalidaExeception.class);
        assertThrows(RequisicaoInvalidaExeception.class, () -> this.clienteV1Controller.buscarClientesPorFiltro(null, null));
    }

    @Test
    void quandoParametrosInvalidos() {
        when(this.clienteV1Service.buscarClientePorFiltros(Strings.EMPTY, null)).thenThrow(RequisicaoInvalidaExeception.class);
        assertThrows(RequisicaoInvalidaExeception.class, () -> this.clienteV1Controller.buscarClientesPorFiltro(Strings.EMPTY, null));
    }
}
