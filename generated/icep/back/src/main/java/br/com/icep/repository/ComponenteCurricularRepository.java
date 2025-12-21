package br.com.icep.repository;

import br.com.icep.entity.ComponenteCurricular;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para ComponenteCurricular.
 */
@Repository
public interface ComponenteCurricularRepository extends JpaRepository<ComponenteCurricular, Integer>, JpaSpecificationExecutor<ComponenteCurricular> {

    /**
     * Busca por Nome (contém).
     */
    List<ComponenteCurricular> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca por Sigla (contém).
     */
    List<ComponenteCurricular> findBySiglaContainingIgnoreCase(String sigla);

}
