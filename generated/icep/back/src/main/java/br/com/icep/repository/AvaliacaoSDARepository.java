package br.com.icep.repository;

import br.com.icep.entity.AvaliacaoSDA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para AvaliacaoSDA.
 */
@Repository
public interface AvaliacaoSDARepository extends JpaRepository<AvaliacaoSDA, Integer>, JpaSpecificationExecutor<AvaliacaoSDA> {

    /**
     * Busca por Avaliação.
     */
    List<AvaliacaoSDA> findByAvaliacaoId(Integer avaliacaoId);

    /**
     * Busca por Município.
     */
    List<AvaliacaoSDA> findByMunicipioId(Integer municipioId);

    /**
     * Busca por Ano Escolar.
     */
    List<AvaliacaoSDA> findByAnoEscolarId(Integer anoEscolarId);

    /**
     * Busca por Aprendizagem Esperada.
     */
    List<AvaliacaoSDA> findByAprendizagemId(Integer aprendizagemId);

    /**
     * Busca paginada filtrada por municipioId.
     */
    Page<AvaliacaoSDA> findByMunicipioId(Long municipioId, Pageable pageable);

    /**
     * Busca paginada filtrada por avaliacaoId.
     */
    Page<AvaliacaoSDA> findByAvaliacaoId(Long avaliacaoId, Pageable pageable);

    /**
     * Busca paginada filtrada por municipioId e avaliacaoId.
     */
    Page<AvaliacaoSDA> findByMunicipioIdAndAvaliacaoId(Long municipioId, Long avaliacaoId, Pageable pageable);

}
