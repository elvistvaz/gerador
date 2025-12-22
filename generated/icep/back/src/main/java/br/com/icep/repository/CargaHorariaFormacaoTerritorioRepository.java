package br.com.icep.repository;

import br.com.icep.entity.CargaHorariaFormacaoTerritorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para CargaHorariaFormacaoTerritorio.
 */
@Repository
public interface CargaHorariaFormacaoTerritorioRepository extends JpaRepository<CargaHorariaFormacaoTerritorio, Integer>, JpaSpecificationExecutor<CargaHorariaFormacaoTerritorio> {

    /**
     * Busca por Avaliação.
     */
    List<CargaHorariaFormacaoTerritorio> findByAvaliacaoId(Integer avaliacaoId);

    /**
     * Busca por Território.
     */
    List<CargaHorariaFormacaoTerritorio> findByTerritorioId(Integer territorioId);

    /**
     * Busca por Tipo de Formação.
     */
    List<CargaHorariaFormacaoTerritorio> findByFormacaoTerritorioId(Integer formacaoTerritorioId);

    /**
     * Busca paginada filtrada por avaliacaoId.
     */
    Page<CargaHorariaFormacaoTerritorio> findByAvaliacaoId(Integer avaliacaoId, Pageable pageable);

}
