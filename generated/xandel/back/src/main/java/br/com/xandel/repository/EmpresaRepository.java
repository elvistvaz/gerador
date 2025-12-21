package br.com.xandel.repository;

import br.com.xandel.entity.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Empresa.
 */
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>, JpaSpecificationExecutor<Empresa> {

    /**
     * Busca por Razão Social (contém).
     */
    List<Empresa> findByNomeEmpresaContainingIgnoreCase(String nomeEmpresa);

}
