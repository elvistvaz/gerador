package br.com.xandel.repository;

import br.com.xandel.entity.NF;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import br.com.xandel.entity.NFId;

import java.util.List;
import java.util.Optional;

/**
 * Repository para NF.
 */
@Repository
public interface NFRepository extends JpaRepository<NF, NFId>, JpaSpecificationExecutor<NF> {

    /**
     * Busca paginada filtrada por idEmpresa.
     */
    Page<NF> findById_IdEmpresa(Integer idEmpresa, Pageable pageable);

}
