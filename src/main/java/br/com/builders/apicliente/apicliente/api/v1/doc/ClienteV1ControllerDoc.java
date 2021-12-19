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
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
    ClienteV1ResponseDoc atualizarCliente(@PathVariable Long id, @RequestBody @Valid AtualizarClienteV1Request atualizarClienteRequest);

    @Operation(description = "Remove o cadastro de um cliente no sistema", summary = "Remove cadastro cliente")
    @ApiResponse(responseCode = "200", description = "Cliente removido com sucesso")
    ClienteV1ResponseDoc removerCliente(@PathVariable Long id);

    @Operation(description = "Buscar o cadastro de um cliente pelo Id", summary = "Busca por Id")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    ClienteV1ResponseDoc buscarClientePorId(@PathVariable Long id);

    @Operation(summary = "Obter clientes filtrados", description = "Obtém resgistros dos clientes cadastrados pelos filtros informados na API.")
    @Parameter(in = ParameterIn.QUERY, name = "nome", description = "Nome do cleinte que será utilizado como filtro verificando se contém em uma parte do nome.")
    @Parameter(in = ParameterIn.QUERY, name = "dataCadastro", description = "Data de cadastro do cleinte que será utilizado como filtro verificando se existe algum igual.")
    @ApiResponse(responseCode = "200", description = "Cadastros dos clientes filtrados com sucesso.")
    Set<ClienteV1ResponseDoc> buscarClientesPorFiltro(@RequestParam(value = "nome", required = false) String nome,
                                                      @RequestParam(value = "idade", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCadastro);

    @Operation(summary = "Obter todos os clientes", description = "Obtém resgistros de todos os clientes cadastrados paginados.")
    @Parameter(in = ParameterIn.QUERY, name = "page", required = true, description = "Número da página para a paginação. Caso não seja informado a paginação será desligada.")
    @Parameter(in = ParameterIn.QUERY, name = "size", required = true, description = "Quantidade de itens por página para a paginação. Caso não seja o número da página a paginação será desligada.")
    @ApiResponse(responseCode = "200", description = "Cadastros dos clientes retornados com sucesso.")
    Page<ClienteV1ResponseDoc> buscarTodosClientesPaginados(Pageable pageable);
}
