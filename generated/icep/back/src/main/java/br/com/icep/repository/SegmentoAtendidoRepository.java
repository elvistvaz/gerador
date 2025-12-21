package br.com.icep.repository;

import br.com.icep.entity.SegmentoAtendido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para SegmentoAtendido.
 */
@Repository
public interface SegmentoAtendidoRepository extends JpaRepository<SegmentoAtendido, Integer>, JpaSpecificationExecutor<SegmentoAtendido> {

    /**
     * Busca por Nome (contém).
     */
    List<SegmentoAtendido> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca por Público Alvo.
     */
    List<SegmentoAtendido> findByPublicoAlvoId(Integer publicoAlvoId);

}
