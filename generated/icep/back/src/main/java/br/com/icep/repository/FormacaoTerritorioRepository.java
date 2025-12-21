package br.com.icep.repository;

import br.com.icep.entity.FormacaoTerritorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para FormacaoTerritorio.
 */
@Repository
public interface FormacaoTerritorioRepository extends JpaRepository<FormacaoTerritorio, Integer>, JpaSpecificationExecutor<FormacaoTerritorio> {

    /**
     * Busca por Nome (contém).
     */
    List<FormacaoTerritorio> findByNomeContainingIgnoreCase(String nome);

}
