package br.com.xandel.repository;

import br.com.xandel.entity.Conselho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Conselho.
 */
@Repository
public interface ConselhoRepository extends JpaRepository<Conselho, Integer>, JpaSpecificationExecutor<Conselho> {

    /**
     * Busca por Nome do Conselho (contém).
     */
    List<Conselho> findByNomeConselhoContainingIgnoreCase(String nomeConselho);

    /**
     * Busca por Sigla (contém).
     */
    List<Conselho> findBySiglaContainingIgnoreCase(String sigla);

}
