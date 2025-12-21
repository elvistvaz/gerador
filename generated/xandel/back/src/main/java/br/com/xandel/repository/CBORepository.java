package br.com.xandel.repository;

import br.com.xandel.entity.CBO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para CBO.
 */
@Repository
public interface CBORepository extends JpaRepository<CBO, String>, JpaSpecificationExecutor<CBO> {

    /**
     * Busca por Código CBO (contém).
     */
    List<CBO> findByIdCBOContainingIgnoreCase(String idCBO);

    /**
     * Busca por Descrição (contém).
     */
    List<CBO> findByNomeCBOContainingIgnoreCase(String nomeCBO);

}
