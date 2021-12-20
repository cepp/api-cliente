package br.com.builders.apicliente.apicliente.api.v1.service;

import br.com.builders.apicliente.apicliente.api.v1.converter.ClienteConverter;
import br.com.builders.apicliente.apicliente.api.v1.doc.ClienteV1ResponseDoc;
import br.com.builders.apicliente.apicliente.api.v1.model.AtualizarClienteV1Request;
import br.com.builders.apicliente.apicliente.api.v1.model.CriarClienteV1Request;
import br.com.builders.apicliente.apicliente.exception.ClienteExistenteException;
import br.com.builders.apicliente.apicliente.exception.ClienteNaoExisteException;
import br.com.builders.apicliente.apicliente.exception.RequisicaoInvalidaExeception;
import br.com.builders.apicliente.apicliente.repository.ClienteRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class ClienteV1Service {
    @Resource
    private ClienteRepository clienteRepository;
    @Resource
    private ClienteConverter clienteConverter;

    @Transactional
    public ClienteV1ResponseDoc incluirCliente(CriarClienteV1Request clienteRequest) {
        if (this.clienteRepository.existsClienteByCpf(clienteRequest.getCpf())) {
            throw new ClienteExistenteException();
        }

        var cliente = this.clienteConverter.criarRequestParaCliente(clienteRequest);
        this.clienteRepository.save(cliente);

        return this.clienteConverter.clienteParaResponse(cliente);
    }

    @Transactional
    public ClienteV1ResponseDoc atualizarCliente(Long id, AtualizarClienteV1Request atualizarClienteRequest) {
        if (this.clienteRepository.existsClienteByIdNotAndCpf(id, atualizarClienteRequest.getCpf())) {
            throw new ClienteExistenteException();
        }

        var cliente = this.clienteRepository.getClienteById(id).orElseThrow(ClienteNaoExisteException::new);

        var clienteAlterado = this.clienteConverter.atualiarRequestParaCliente(atualizarClienteRequest, cliente);

        this.clienteRepository.save(clienteAlterado);

        return this.clienteConverter.clienteParaResponse(clienteAlterado);
    }

    @Transactional
    public ClienteV1ResponseDoc removerCliente(Long id) {
        var cliente = this.clienteRepository.getClienteById(id).orElseThrow(ClienteNaoExisteException::new);
        var clienteResponse = this.clienteConverter.clienteParaResponse(cliente);
        this.clienteRepository.delete(cliente);
        return clienteResponse;
    }

    @Transactional(readOnly = true)
    public ClienteV1ResponseDoc buscarClientePorId(Long id) {
        var optionalCliente = this.clienteRepository.getClienteById(id);
        return optionalCliente.map(cliente -> this.clienteConverter.clienteParaResponse(cliente)).orElse(null);
    }

    @Transactional(readOnly = true)
    public Set<ClienteV1ResponseDoc> buscarClientePorFiltros(String nome, LocalDate dataCadastro) {
        if (StringUtils.isBlank(nome) && isNull(dataCadastro)) {
            throw new RequisicaoInvalidaExeception();
        }

        var clientes = this.clienteRepository.findClienteByFiltrado(nome, dataCadastro);
        if (CollectionUtils.isEmpty(clientes)) {
            return Collections.emptySet();
        }
        return clientes.stream().map(this.clienteConverter::clienteParaResponse).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Page<ClienteV1ResponseDoc> buscarClientePaginado(Pageable pageable) {
        return this.clienteRepository.findAll(pageable).map(this.clienteConverter::clienteParaResponse);
    }
}
