package br.com.icep.repository;

import br.com.icep.entity.Formacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Formacao.
 */
@Repository
public interface FormacaoRepository extends JpaRepository<Formacao, Integer>, JpaSpecificationExecutor<Formacao> {

    /**
     * Busca por Nome (contém).
     */
    List<Formacao> findByNomeContainingIgnoreCase(String nome);

}
