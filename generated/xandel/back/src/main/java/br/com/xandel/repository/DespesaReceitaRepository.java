package br.com.xandel.repository;

import br.com.xandel.entity.DespesaReceita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para DespesaReceita.
 */
@Repository
public interface DespesaReceitaRepository extends JpaRepository<DespesaReceita, Integer>, JpaSpecificationExecutor<DespesaReceita> {

    /**
     * Busca por Sigla (contém).
     */
    List<DespesaReceita> findBySiglaDespesaReceitaContainingIgnoreCase(String siglaDespesaReceita);

    /**
     * Busca por Nome (contém).
     */
    List<DespesaReceita> findByNomeDespesaReceitaContainingIgnoreCase(String nomeDespesaReceita);

}
