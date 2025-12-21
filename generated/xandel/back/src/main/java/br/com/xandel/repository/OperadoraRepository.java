package br.com.xandel.repository;

import br.com.xandel.entity.Operadora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Operadora.
 */
@Repository
public interface OperadoraRepository extends JpaRepository<Operadora, Integer>, JpaSpecificationExecutor<Operadora> {

    /**
     * Busca por Nome da Operadora (contém).
     */
    List<Operadora> findByNomeOperadoraContainingIgnoreCase(String nomeOperadora);

}
