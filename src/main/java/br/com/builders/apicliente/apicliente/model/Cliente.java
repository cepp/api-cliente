package br.com.builders.apicliente.apicliente.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "nome", length = 150, nullable = false)
    String nome;

    @Column(name = "endereco", nullable = false)
    String endereco;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    String cpf;

    @Column(name = "data_nascimento", nullable = false)
    LocalDate dataNascimento;

    @Column(name = "data_cadastro", nullable = false)
    LocalDate dataCadastro;

    public Cliente() {
        this.id = null;
        this.nome = null;
        this.endereco = null;
        this.cpf = null;
        this.dataCadastro = null;
        this.dataNascimento = null;
    }

    public Cliente(String nome, String endereco, LocalDate dataNascimento, String cpf) {
        this.id = null;
        this.nome = nome;
        this.endereco = endereco;
        this.dataCadastro = LocalDate.now();
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
    }

    public Cliente alteraNome(String nome) {
        return new Cliente(this.getId(), nome, this.getEndereco(), this.getCpf(), this.getDataNascimento(),
                this.getDataCadastro());
    }

    public Cliente alteraEndereco(String endereco) {
        return new Cliente(this.getId(), this.getNome(), endereco, this.getCpf(), this.getDataNascimento(),
                this.getDataCadastro());
    }

    public Cliente alteraCpf(String cpf) {
        return new Cliente(this.getId(), this.getNome(), this.getEndereco(), cpf, this.getDataNascimento(),
                this.getDataCadastro());
    }

    public Cliente alteraDataNascimento(LocalDate dataNascimento) {
        return new Cliente(this.getId(), this.getNome(), this.getEndereco(), this.getCpf(), dataNascimento,
                this.getDataCadastro());
    }

    public Integer getIdade() {
        return Period.between(this.getDataNascimento(), LocalDate.now()).getYears();
    }
}
