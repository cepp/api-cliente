package br.com.builders.apicliente.apicliente.api.v1.controller.ut;

import br.com.builders.apicliente.apicliente.api.v1.controller.ClienteV1Controller;
import br.com.builders.apicliente.apicliente.api.v1.model.ClienteV1Response;
import br.com.builders.apicliente.apicliente.api.v1.model.CriarClienteV1Request;
import br.com.builders.apicliente.apicliente.api.v1.service.ClienteV1Service;
import br.com.builders.apicliente.apicliente.exception.ClienteExistenteException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarClienteV1ControllerTest {
    @InjectMocks
    private ClienteV1Controller clienteV1Controller;
    @Mock
    private ClienteV1Service clienteV1Service;

    @Test
    void quandoClienteCadastradoSucesso() {
        var respostaEsperada = ClienteV1Response.builder().cpf("12345678901")
                .dataCadastro(LocalDate.now()).endereco("endereço aleatório").nome("nome aleatório").idade(11).id(1L).build();
        when(this.clienteV1Service.incluirCliente(any())).thenReturn(respostaEsperada);

        var resposta = this.clienteV1Controller.criarCliente(new CriarClienteV1Request());

        assertEquals(respostaEsperada.getId(), resposta.getId());
        assertEquals(respostaEsperada.getNome(), resposta.getNome());
        assertEquals(respostaEsperada.getCpf(), resposta.getCpf());
        assertEquals(respostaEsperada.getEndereco(), resposta.getEndereco());
        assertEquals(respostaEsperada.getDataCadastro(), resposta.getDataCadastro());
        assertEquals(respostaEsperada.getIdade(), resposta.getIdade());
    }


    @Test
    void quandoClienteJaFoiCadastrado() {
        when(this.clienteV1Service.incluirCliente(any())).thenThrow(ClienteExistenteException.class);
        assertThrows(ClienteExistenteException.class, () -> this.clienteV1Controller.criarCliente(new CriarClienteV1Request()));
    }
}
