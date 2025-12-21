package br.com.xandel.repository;

import br.com.xandel.entity.RepasseAnual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import br.com.xandel.entity.RepasseAnualId;

import java.util.List;
import java.util.Optional;

/**
 * Repository para RepasseAnual.
 */
@Repository
public interface RepasseAnualRepository extends JpaRepository<RepasseAnual, RepasseAnualId>, JpaSpecificationExecutor<RepasseAnual> {

    /**
     * Busca por Ano (contém).
     */
    List<RepasseAnual> findByAnoContainingIgnoreCase(String ano);

    /**
     * Busca paginada filtrada por idEmpresa.
     */
    Page<RepasseAnual> findByIdEmpresa(Integer idEmpresa, Pageable pageable);

}
