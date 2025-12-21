package br.com.xandel.repository;

import br.com.xandel.entity.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Pessoa.
 */
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>, JpaSpecificationExecutor<Pessoa> {

    /**
     * Busca por Nome (contém).
     */
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca por CPF (contém).
     */
    List<Pessoa> findByCpfContainingIgnoreCase(String cpf);

}
