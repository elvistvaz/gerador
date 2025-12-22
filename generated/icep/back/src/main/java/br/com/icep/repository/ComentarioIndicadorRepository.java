package br.com.icep.repository;

import br.com.icep.entity.ComentarioIndicador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para ComentarioIndicador.
 */
@Repository
public interface ComentarioIndicadorRepository extends JpaRepository<ComentarioIndicador, Integer>, JpaSpecificationExecutor<ComentarioIndicador> {

    /**
     * Busca por Avaliação.
     */
    List<ComentarioIndicador> findByAvaliacaoId(Integer avaliacaoId);

    /**
     * Busca por Município.
     */
    List<ComentarioIndicador> findByMunicipioId(Integer municipioId);

    /**
     * Busca por Âmbito de Gestão.
     */
    List<ComentarioIndicador> findByAmbitoGestaoId(Integer ambitoGestaoId);

    /**
     * Busca paginada filtrada por municipioId.
     */
    Page<ComentarioIndicador> findByMunicipioId(Integer municipioId, Pageable pageable);

    /**
     * Busca paginada filtrada por avaliacaoId.
     */
    Page<ComentarioIndicador> findByAvaliacaoId(Integer avaliacaoId, Pageable pageable);

    /**
     * Busca paginada filtrada por municipioId e avaliacaoId.
     */
    Page<ComentarioIndicador> findByMunicipioIdAndAvaliacaoId(Integer municipioId, Integer avaliacaoId, Pageable pageable);

}
