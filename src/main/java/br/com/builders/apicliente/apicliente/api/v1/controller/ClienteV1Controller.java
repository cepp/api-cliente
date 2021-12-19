package br.com.builders.apicliente.apicliente.api.v1.controller;


import br.com.builders.apicliente.apicliente.api.v1.doc.ClienteV1ControllerDoc;
import br.com.builders.apicliente.apicliente.api.v1.doc.ClienteV1ResponseDoc;
import br.com.builders.apicliente.apicliente.api.v1.model.AtualizarClienteV1Request;
import br.com.builders.apicliente.apicliente.api.v1.model.CriarClienteV1Request;
import br.com.builders.apicliente.apicliente.api.v1.service.ClienteV1Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteV1Controller implements ClienteV1ControllerDoc {

    @Resource
    private ClienteV1Service clienteService;

    @Override
    @PostMapping
    public ClienteV1ResponseDoc criarCliente(CriarClienteV1Request criarClienteRequest) {
        return this.clienteService.incluirCliente(criarClienteRequest);
    }

    @Override
    @PutMapping("/{id}")
    public ClienteV1ResponseDoc atualizarCliente(Long id, AtualizarClienteV1Request atualizarClienteRequest) {
        return this.clienteService.atualizarCliente(id, atualizarClienteRequest);
    }

    @Override
    @DeleteMapping("/{id}")
    public ClienteV1ResponseDoc removerCliente(Long id) {
        return this.clienteService.removerCliente(id);
    }

    @Override
    @GetMapping("/{id}")
    public ClienteV1ResponseDoc buscarClientePorId(Long id) {
        return this.clienteService.buscarClientePorId(id);
    }

    @Override
    @GetMapping("/filtrado")
    public Set<ClienteV1ResponseDoc> buscarClientesPorFiltro(String nome, LocalDate dataCadastro) {
        return this.clienteService.buscarClientePorFiltros(nome, dataCadastro);
    }

    @Override
    @GetMapping
    public Page<ClienteV1ResponseDoc> buscarTodosClientesPaginados(Pageable pageable) {
        return this.clienteService.buscarClientePaginado(pageable);
    }
}
