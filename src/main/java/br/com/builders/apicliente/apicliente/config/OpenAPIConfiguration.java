package br.com.builders.apicliente.apicliente.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    public static final String MSG_BAD_REQUEST = "Parâmetros passados no request inválidos.";
    public static final String MSG_NOT_FOUND = "Recurso não encontrado.";
    private static final String MSG_INTERNAL_ERROR = "Erro interno da aplicação não esperado.";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Cadastro Clientes")
                        .version("v1")
                        .contact(new Contact().name("Carlos Pantoja").email("cepsolutionsltds@gmail.com").url("https://www.linkedin.com/in/ceppantoja/"))
                        .description("API - Cadastro de clientes para o MVP e avaliação na Builders"))
                        .servers(this.servidores());

    }

    private List<Server> servidores() {
        var servidorProducao = new Server();
        servidorProducao.setUrl("https://apicliente-builders.herokuapp.com/api-cliente");
        servidorProducao.setDescription("Servidor de produção do MVP no Heroku");

        var servidorLocal = new Server();
        servidorLocal.setUrl("http://localhost:8080/api-cliente");
        servidorLocal.setDescription("Servidor local do MVP");
        return Arrays.asList(servidorLocal, servidorProducao);
    }

    @Bean
    public OpenApiCustomiser customGlobalHeaderOpenApiCustomiser() {
        ApiResponse methodNotAllowed = new ApiResponse().description("Verbo HTTP utilizado não é suportado.");

        ApiResponse badRequest = new ApiResponse()
                .description("Requisição inválida.")
                .content(new Content().addMediaType(
                        org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                        new MediaType().addExamples("Retorno Exemplo",
                                new Example()
                                        .description(MSG_BAD_REQUEST)
                                        .value(MSG_BAD_REQUEST)
                        )
                ));

        ApiResponse notFound = new ApiResponse()
                .description("Recurso não encontrado.")
                .content(new Content().addMediaType(
                        org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                        new MediaType().addExamples("Retorno Exemplo",
                                new Example()
                                        .description(MSG_NOT_FOUND)
                                        .value(MSG_NOT_FOUND)
                        )
                ));

        ApiResponse error = new ApiResponse()
                .description("Erro interno no servidor.")
                .content(new Content().addMediaType(
                        org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                        new MediaType().addExamples("Retorno Exemplo",
                                new Example()
                                        .description(MSG_INTERNAL_ERROR)
                                        .value(MSG_INTERNAL_ERROR)
                        )
                ));

        return openApi -> openApi.getPaths().values().forEach(
                controller -> controller.readOperations().forEach(
                        documentacaoEndpoint -> {
                            documentacaoEndpoint.getResponses().addApiResponse("400", badRequest);
                            documentacaoEndpoint.getResponses().addApiResponse("404", notFound);
                            documentacaoEndpoint.getResponses().addApiResponse("500", error);
                        }
                )
        );
    }
}
