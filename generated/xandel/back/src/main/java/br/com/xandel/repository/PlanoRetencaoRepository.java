package br.com.xandel.repository;

import br.com.xandel.entity.PlanoRetencao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para PlanoRetencao.
 */
@Repository
public interface PlanoRetencaoRepository extends JpaRepository<PlanoRetencao, Integer>, JpaSpecificationExecutor<PlanoRetencao> {

    /**
     * Busca por Nome do Plano (contém).
     */
    List<PlanoRetencao> findByNomePlanoRetencaoContainingIgnoreCase(String nomePlanoRetencao);

}
