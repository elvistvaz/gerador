package br.com.xandel.repository;

import br.com.xandel.entity.EmpresaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import br.com.xandel.entity.EmpresaClienteId;

import java.util.List;
import java.util.Optional;

/**
 * Repository para EmpresaCliente.
 */
@Repository
public interface EmpresaClienteRepository extends JpaRepository<EmpresaCliente, EmpresaClienteId>, JpaSpecificationExecutor<EmpresaCliente> {

    /**
     * Busca por Empresa.
     */
    List<EmpresaCliente> findById_IdEmpresa(Integer idEmpresa);

    /**
     * Busca por Cliente.
     */
    List<EmpresaCliente> findById_IdCliente(Integer idCliente);

}
