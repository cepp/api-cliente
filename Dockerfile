FROM openjdk:11

WORKDIR /opt/apis

COPY target/*.jar cadastro-clientes.jar

EXPOSE 8080

CMD ["java", "-jar", "cadastro-clientes.jar"]
