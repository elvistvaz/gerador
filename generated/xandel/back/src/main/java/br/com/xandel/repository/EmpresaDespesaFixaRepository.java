package br.com.xandel.repository;

import br.com.xandel.entity.EmpresaDespesaFixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import br.com.xandel.entity.EmpresaDespesaFixaId;

import java.util.List;
import java.util.Optional;

/**
 * Repository para EmpresaDespesaFixa.
 */
@Repository
public interface EmpresaDespesaFixaRepository extends JpaRepository<EmpresaDespesaFixa, EmpresaDespesaFixaId>, JpaSpecificationExecutor<EmpresaDespesaFixa> {

    /**
     * Busca por Empresa.
     */
    List<EmpresaDespesaFixa> findById_IdEmpresa(Integer idEmpresa);

    /**
     * Busca por Despesa/Receita.
     */
    List<EmpresaDespesaFixa> findById_IdDespesaReceita(Integer idDespesaReceita);

}
