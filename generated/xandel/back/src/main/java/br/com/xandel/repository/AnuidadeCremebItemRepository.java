package br.com.xandel.repository;

import br.com.xandel.entity.AnuidadeCremebItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para AnuidadeCremebItem.
 */
@Repository
public interface AnuidadeCremebItemRepository extends JpaRepository<AnuidadeCremebItem, Integer>, JpaSpecificationExecutor<AnuidadeCremebItem> {

    /**
     * Busca por Anuidade.
     */
    List<AnuidadeCremebItem> findByIdAnuidadeCremeb(Integer idAnuidadeCremeb);

    /**
     * Busca por Pessoa.
     */
    List<AnuidadeCremebItem> findByIdPessoa(Integer idPessoa);

    /**
     * Busca por Lançamento.
     */
    List<AnuidadeCremebItem> findByIdLancamento(Integer idLancamento);

}
