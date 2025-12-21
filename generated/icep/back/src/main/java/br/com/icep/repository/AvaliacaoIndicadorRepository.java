package br.com.icep.repository;

import br.com.icep.entity.AvaliacaoIndicador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para AvaliacaoIndicador.
 */
@Repository
public interface AvaliacaoIndicadorRepository extends JpaRepository<AvaliacaoIndicador, Integer>, JpaSpecificationExecutor<AvaliacaoIndicador> {

    /**
     * Busca por Avaliação.
     */
    List<AvaliacaoIndicador> findByAvaliacaoId(Integer avaliacaoId);

    /**
     * Busca por Município.
     */
    List<AvaliacaoIndicador> findByMunicipioId(Integer municipioId);

    /**
     * Busca por Indicador.
     */
    List<AvaliacaoIndicador> findByIndicadorId(Integer indicadorId);

    /**
     * Busca paginada filtrada por municipioId.
     */
    Page<AvaliacaoIndicador> findByMunicipioId(Long municipioId, Pageable pageable);

    /**
     * Busca paginada filtrada por avaliacaoId.
     */
    Page<AvaliacaoIndicador> findByAvaliacaoId(Long avaliacaoId, Pageable pageable);

    /**
     * Busca paginada filtrada por municipioId e avaliacaoId.
     */
    Page<AvaliacaoIndicador> findByMunicipioIdAndAvaliacaoId(Long municipioId, Long avaliacaoId, Pageable pageable);

}
