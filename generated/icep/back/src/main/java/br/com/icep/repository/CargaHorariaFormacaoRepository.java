package br.com.icep.repository;

import br.com.icep.entity.CargaHorariaFormacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para CargaHorariaFormacao.
 */
@Repository
public interface CargaHorariaFormacaoRepository extends JpaRepository<CargaHorariaFormacao, Integer>, JpaSpecificationExecutor<CargaHorariaFormacao> {

    /**
     * Busca por Avaliação.
     */
    List<CargaHorariaFormacao> findByAvaliacaoId(Integer avaliacaoId);

    /**
     * Busca por Município.
     */
    List<CargaHorariaFormacao> findByMunicipioId(Integer municipioId);

    /**
     * Busca por Formação.
     */
    List<CargaHorariaFormacao> findByFormacaoId(Integer formacaoId);

    /**
     * Busca paginada filtrada por municipioId.
     */
    Page<CargaHorariaFormacao> findByMunicipioId(Integer municipioId, Pageable pageable);

    /**
     * Busca paginada filtrada por avaliacaoId.
     */
    Page<CargaHorariaFormacao> findByAvaliacaoId(Integer avaliacaoId, Pageable pageable);

    /**
     * Busca paginada filtrada por municipioId e avaliacaoId.
     */
    Page<CargaHorariaFormacao> findByMunicipioIdAndAvaliacaoId(Integer municipioId, Integer avaliacaoId, Pageable pageable);

}
