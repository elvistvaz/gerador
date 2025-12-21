package br.com.xandel.repository;

import br.com.xandel.entity.PessoaContaRecebimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import br.com.xandel.entity.PessoaContaRecebimentoId;

import java.util.List;
import java.util.Optional;

/**
 * Repository para PessoaContaRecebimento.
 */
@Repository
public interface PessoaContaRecebimentoRepository extends JpaRepository<PessoaContaRecebimento, PessoaContaRecebimentoId>, JpaSpecificationExecutor<PessoaContaRecebimento> {

    /**
     * Busca por Pessoa.
     */
    List<PessoaContaRecebimento> findById_IdPessoa(Integer idPessoa);

    /**
     * Busca por Cliente.
     */
    List<PessoaContaRecebimento> findById_IdCliente(Integer idCliente);

    /**
     * Busca por Conta Corrente.
     */
    List<PessoaContaRecebimento> findById_IdContaCorrente(Integer idContaCorrente);

}
