package br.com.xandel.repository;

import br.com.xandel.entity.PagamentoNaoSocio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import br.com.xandel.entity.PagamentoNaoSocioId;

import java.util.List;
import java.util.Optional;

/**
 * Repository para PagamentoNaoSocio.
 */
@Repository
public interface PagamentoNaoSocioRepository extends JpaRepository<PagamentoNaoSocio, PagamentoNaoSocioId>, JpaSpecificationExecutor<PagamentoNaoSocio> {

    /**
     * Busca paginada filtrada por idEmpresa.
     */
    Page<PagamentoNaoSocio> findById_IdEmpresa(Integer idEmpresa, Pageable pageable);

}
