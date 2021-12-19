package br.com.builders.apicliente.apicliente.api.v1.controller.ut;

import br.com.builders.apicliente.apicliente.api.v1.controller.ClienteV1Controller;
import br.com.builders.apicliente.apicliente.api.v1.doc.ClienteV1ResponseDoc;
import br.com.builders.apicliente.apicliente.api.v1.model.ClienteV1Response;
import br.com.builders.apicliente.apicliente.api.v1.service.ClienteV1Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarPaginadoClienteV1ControllerTest {
    @InjectMocks
    private ClienteV1Controller clienteV1Controller;
    @Mock
    private ClienteV1Service clienteV1Service;

    @Test
    void quandoClienteEncontradoSucesso() {
        var respostaEsperada = ClienteV1Response.builder().cpf("12345678901")
                .dataCadastro(LocalDate.now()).endereco("endereço aleatório").nome("nome aleatório").idade(11).id(1L).build();

        var pageable = PageRequest.of(1, 20);
        var pageResponse = new PageImpl<ClienteV1ResponseDoc>(List.of(respostaEsperada), pageable, 1);

        when(this.clienteV1Service.buscarClientePaginado(pageable)).thenReturn(pageResponse);

        var resposta = this.clienteV1Controller.buscarTodosClientesPaginados(pageable);

        assertNotNull(resposta);
        assertFalse(resposta.isEmpty());
        assertEquals(pageResponse, resposta);
    }


    @Test
    void quandoClienteNaoCadastrado() {
        var pageable = PageRequest.of(1, 20);
        Page<ClienteV1ResponseDoc> pageResponse = Page.empty(pageable);

        when(this.clienteV1Service.buscarClientePaginado(pageable)).thenReturn(pageResponse);

        var resposta = this.clienteV1Controller.buscarTodosClientesPaginados(pageable);

        assertNotNull(resposta);
        assertTrue(resposta.isEmpty());
    }
}
