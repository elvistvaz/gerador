package br.com.xandel.repository;

import br.com.xandel.entity.Especialidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Especialidade.
 */
@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Integer>, JpaSpecificationExecutor<Especialidade> {

    /**
     * Busca por Nome da Especialidade (contém).
     */
    List<Especialidade> findByNomeEspecialidadeContainingIgnoreCase(String nomeEspecialidade);

}
