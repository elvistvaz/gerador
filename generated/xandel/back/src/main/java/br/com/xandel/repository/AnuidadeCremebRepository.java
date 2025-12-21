package br.com.xandel.repository;

import br.com.xandel.entity.AnuidadeCremeb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para AnuidadeCremeb.
 */
@Repository
public interface AnuidadeCremebRepository extends JpaRepository<AnuidadeCremeb, Integer>, JpaSpecificationExecutor<AnuidadeCremeb> {

    /**
     * Busca por Ano Exercício (contém).
     */
    List<AnuidadeCremeb> findByAnoExercicioContainingIgnoreCase(String anoExercicio);

    /**
     * Busca paginada filtrada por idEmpresa.
     */
    Page<AnuidadeCremeb> findByIdEmpresa(Integer idEmpresa, Pageable pageable);

}
