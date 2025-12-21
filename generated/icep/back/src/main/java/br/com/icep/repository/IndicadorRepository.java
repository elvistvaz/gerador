package br.com.icep.repository;

import br.com.icep.entity.Indicador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Indicador.
 */
@Repository
public interface IndicadorRepository extends JpaRepository<Indicador, Integer>, JpaSpecificationExecutor<Indicador> {

    /**
     * Busca por Descrição (contém).
     */
    List<Indicador> findByDescricaoContainingIgnoreCase(String descricao);

    /**
     * Busca por Âmbito de Gestão.
     */
    List<Indicador> findByAmbitoGestaoId(Integer ambitoGestaoId);

}
