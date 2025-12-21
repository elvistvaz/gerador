package br.com.xandel.repository;

import br.com.xandel.entity.ContasPagarReceber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para ContasPagarReceber.
 */
@Repository
public interface ContasPagarReceberRepository extends JpaRepository<ContasPagarReceber, Integer>, JpaSpecificationExecutor<ContasPagarReceber> {

    /**
     * Busca por Histórico (contém).
     */
    List<ContasPagarReceber> findByHistoricoContainingIgnoreCase(String historico);

    /**
     * Busca paginada filtrada por idEmpresa.
     */
    Page<ContasPagarReceber> findByIdEmpresa(Integer idEmpresa, Pageable pageable);

}
