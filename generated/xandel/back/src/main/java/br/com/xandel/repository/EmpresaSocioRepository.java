package br.com.xandel.repository;

import br.com.xandel.entity.EmpresaSocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import br.com.xandel.entity.EmpresaSocioId;

import java.util.List;
import java.util.Optional;

/**
 * Repository para EmpresaSocio.
 */
@Repository
public interface EmpresaSocioRepository extends JpaRepository<EmpresaSocio, EmpresaSocioId>, JpaSpecificationExecutor<EmpresaSocio> {

    /**
     * Busca por Empresa.
     */
    List<EmpresaSocio> findById_IdEmpresa(Integer idEmpresa);

    /**
     * Busca por Sócio.
     */
    List<EmpresaSocio> findById_IdPessoa(Integer idPessoa);

}
