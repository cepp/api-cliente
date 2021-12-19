# MVP Cadastro Clientes - Builders

Este projeto foi criado com o intuito de participar do processo seletivo da Builders.

### Requisitos

- Desenvolva uma REST API que:
- Permita criação de novos clientes;
- Permita a atualização de clientes existentes;
- Permita que seja possível listar os clientes de forma paginada;
- Permita que buscas por atributos cadastrais do cliente;
- É necessário também que cada elemento retornado pela api de clientes informe a idade;
- Documente sua API e também disponibilize um arquivo Postman para fácil utilização da API.

### Solução proposta
Para atender aos requistos do exame doram utilizadas as seguintes linguagens e framworks:
- Linguagem Java na versão 11
- Framework Spring Boot 2.6.1
- Maven como controle de dependências
- Springdoc para fazer a documentação da API usando o OpenAPI 3.0
- Spring Data para integração com o banco de dados
- PostgreSQL como banco de dados principal da aplicação
- H2 como banco de dados de testes de integração
- Mockito para os testes unitários
- Spring validation para tratar os dados do request
- Spring actuator para verificar a saúde da aplicação
- Lombok para deixar o projeto menos verboso
- Biblioteca apache commons collections para facilitar tratamento de collections
- Biblioteca jackson-datatype-jsr310 para que funcione o ObjectMapper com LocalDate


### Testes
Foram implementados os testes unitários das classes Service, Controller e Converter, utilizando mockito e JUnit 5.

Os testes de integração foram implementados utilizando o MockMvc do Spring onde foi feito a chamada de todos os Endpoints
criados para esse MVP, com seus possíveis casos de sucesso e as falhas identificadas como importantes de se tratar.

Para fazer o teste da integração com o banco de dados foi utilizado o banco de dados H2, não foi utilizado o Postgres, 
porque configurar o testcontainer para um MVP demoraria muito tempo para fazer.

### Rodar aplicação
Para rodar a aplicação é preciso construir o projeto usando o maven e Java 11, seguindo o comando abaixo:
> mvn install

No processo de construção os testes unitários e de integração serão executados antes de criar o pacote, caso algum teste
falhe, a construção do pacote é abortada.

Após a construção do projeto com sucesso, será criado na pasta target o jar do projeto, que pode ser executado, se já 
possuir uma instância de PostgreSQL rodando com as configuraçÕes de conexão do _application.yaml_, com o comando:
> java -jar api-cliente-0.0.1-SNAPSHOT.jar

Também existe a possibilidade de inicializar o projeto utilizando o Docker. Executando o comando:
> docker-compose build && docker-compose up -d

### Documentação Local
A URL para acessar a [documentação do projeto](http://localhost:8080/api-cliente/api-docs), caso quer ver a documentação de uma forma mais amigável, segue o [link do swagger](http://localhost:8080/api-cliente/swagger-ui/index.html).

### Documentação Heroku
A URL para acessar a [documentação do projeto](http://localhost:8080/api-cliente/api-docs), caso quer ver a documentação de uma forma mais amigável, segue o [link do swagger](http://localhost:8080/api-cliente/swagger-ui/index.html).

