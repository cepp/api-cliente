package br.com.builders.apicliente.apicliente.repository;

import br.com.builders.apicliente.apicliente.model.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long> {
    boolean existsClienteByCpf(String cpf);
    Optional<Cliente> getClienteById(Long id);

    @Query(value = "FROM Cliente WHERE (:nome is null OR nome like %:nome%) AND (:dataCadastro is null OR dataCadastro = :dataCadastro)")
    Set<Cliente> findClienteByFiltrado(String nome, LocalDate dataCadastro);

}
