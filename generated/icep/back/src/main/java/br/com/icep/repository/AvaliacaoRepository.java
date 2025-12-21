package br.com.icep.repository;

import br.com.icep.entity.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Avaliacao.
 */
@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer>, JpaSpecificationExecutor<Avaliacao> {

    /**
     * Busca por Avaliação (contém).
     */
    List<Avaliacao> findByAvaliacaoContainingIgnoreCase(String avaliacao);

}
