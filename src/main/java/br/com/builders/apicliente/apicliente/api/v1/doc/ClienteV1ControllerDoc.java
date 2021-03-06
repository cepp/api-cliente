package br.com.builders.apicliente.apicliente.api.v1.doc;


import br.com.builders.apicliente.apicliente.api.v1.model.AtualizarClienteV1Request;
import br.com.builders.apicliente.apicliente.api.v1.model.CriarClienteV1Request;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Set;

@Validated
@Tag(name = "Cliente", description = "API de cadastro e consulta de clientes")
public interface ClienteV1ControllerDoc {

    @Operation(description = "Inclui o cadastro de um cliente no sistema", summary = "Cadastro cliente")
    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso")
    @ResponseStatus(HttpStatus.CREATED)
    ClienteV1ResponseDoc criarCliente(@RequestBody @Valid CriarClienteV1Request criarClienteRequest);

    @Operation(description = "Atualiza o cadastro de um cliente no sistema", summary = "Atualiza cadastro cliente")
    @Parameter(in = ParameterIn.PATH, name = "id", example = "1", description = "Identificador do cadastro do cliente.")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
    ClienteV1ResponseDoc atualizarCliente(@PathVariable Long id, @RequestBody @Valid AtualizarClienteV1Request atualizarClienteRequest);

    @Operation(description = "Remove o cadastro de um cliente no sistema", summary = "Remove cadastro cliente")
    @Parameter(in = ParameterIn.PATH, name = "id", example = "1", description = "Identificador do cadastro do cliente.")
    @ApiResponse(responseCode = "200", description = "Cliente removido com sucesso")
    ClienteV1ResponseDoc removerCliente(@PathVariable Long id);

    @Operation(description = "Buscar o cadastro de um cliente pelo Id", summary = "Busca por Id")
    @Parameter(in = ParameterIn.PATH, name = "id", example = "1", description = "Identificador do cadastro do cliente.")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    ClienteV1ResponseDoc buscarClientePorId(@PathVariable Long id);

    @Operation(summary = "Obter clientes filtrados", description = "Obt??m resgistros dos clientes cadastrados pelos filtros informados na API.")
    @Parameter(in = ParameterIn.QUERY, name = "nome", example = "Jos?? Manoel", description = "Nome do cliente que ser?? utilizado como filtro verificando se cont??m em uma parte do nome.")
    @Parameter(in = ParameterIn.QUERY, name = "dataCadastro", example = "2021-12-19", description = "Data de cadastro do cliente que ser?? utilizado como filtro verificando se existe algum igual.")
    @ApiResponse(responseCode = "200", description = "Cadastros dos clientes filtrados com sucesso.")
    Set<ClienteV1ResponseDoc> buscarClientesPorFiltro(@RequestParam(value = "nome", required = false) String nome,
                                                      @RequestParam(value = "dataCadastro", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCadastro);

    @Operation(summary = "Obter todos os clientes", description = "Obt??m resgistros de todos os clientes cadastrados paginados.")
    @ApiResponse(responseCode = "200", description = "Cadastros dos clientes retornados com sucesso.")
    Page<ClienteV1ResponseDoc> buscarTodosClientesPaginados(Pageable pageable);
}
