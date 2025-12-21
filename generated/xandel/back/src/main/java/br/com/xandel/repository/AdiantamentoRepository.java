package br.com.xandel.repository;

import br.com.xandel.entity.Adiantamento;
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
     * Busca por Empresa.
     */
    List<Adiantamento> findByIdEmpresa(Integer idEmpresa);

    /**
     * Busca por Pessoa.
     */
    List<Adiantamento> findByIdPessoa(Integer idPessoa);

    /**
     * Busca por Cliente.
     */
    List<Adiantamento> findByIdCliente(Integer idCliente);

    /**
     * Busca por Lançamento.
     */
    List<Adiantamento> findByIdLancamento(Integer idLancamento);

}
