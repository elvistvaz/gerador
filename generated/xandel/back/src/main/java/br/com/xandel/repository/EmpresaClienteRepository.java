package br.com.xandel.repository;

import br.com.xandel.entity.EmpresaCliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

}
