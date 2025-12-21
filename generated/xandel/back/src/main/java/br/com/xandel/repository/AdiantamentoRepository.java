package br.com.xandel.repository;

import br.com.xandel.entity.Adiantamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Adiantamento.
 */
@Repository
public interface AdiantamentoRepository extends JpaRepository<Adiantamento, Integer>, JpaSpecificationExecutor<Adiantamento> {

    /**
     * Busca paginada filtrada por idEmpresa.
     */
    Page<Adiantamento> findByIdEmpresa(Integer idEmpresa, Pageable pageable);

}
