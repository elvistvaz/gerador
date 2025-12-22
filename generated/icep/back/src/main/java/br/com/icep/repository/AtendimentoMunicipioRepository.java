package br.com.icep.repository;

import br.com.icep.entity.AtendimentoMunicipio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para AtendimentoMunicipio.
 */
@Repository
public interface AtendimentoMunicipioRepository extends JpaRepository<AtendimentoMunicipio, Integer>, JpaSpecificationExecutor<AtendimentoMunicipio> {

    /**
     * Busca por Avaliação.
     */
    List<AtendimentoMunicipio> findByAvaliacaoId(Integer avaliacaoId);

    /**
     * Busca por Município.
     */
    List<AtendimentoMunicipio> findByMunicipioId(Integer municipioId);

    /**
     * Busca por Segmento Atendido.
     */
    List<AtendimentoMunicipio> findBySegmentoAtendidoId(Integer segmentoAtendidoId);

    /**
     * Busca paginada filtrada por municipioId.
     */
    Page<AtendimentoMunicipio> findByMunicipioId(Integer municipioId, Pageable pageable);

    /**
     * Busca paginada filtrada por avaliacaoId.
     */
    Page<AtendimentoMunicipio> findByAvaliacaoId(Integer avaliacaoId, Pageable pageable);

    /**
     * Busca paginada filtrada por municipioId e avaliacaoId.
     */
    Page<AtendimentoMunicipio> findByMunicipioIdAndAvaliacaoId(Integer municipioId, Integer avaliacaoId, Pageable pageable);

}
