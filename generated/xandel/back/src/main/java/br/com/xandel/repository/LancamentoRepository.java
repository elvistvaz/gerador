package br.com.xandel.repository;

import br.com.xandel.entity.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Lancamento.
 */
@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Integer>, JpaSpecificationExecutor<Lancamento> {

    /**
     * Busca paginada filtrada por idEmpresa.
     */
    Page<Lancamento> findByIdEmpresa(Integer idEmpresa, Pageable pageable);

}
