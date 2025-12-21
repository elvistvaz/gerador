package br.com.icep.repository;

import br.com.icep.entity.AmbitoGestao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para AmbitoGestao.
 */
@Repository
public interface AmbitoGestaoRepository extends JpaRepository<AmbitoGestao, Integer>, JpaSpecificationExecutor<AmbitoGestao> {

    /**
     * Busca por Nome (contém).
     */
    List<AmbitoGestao> findByNomeContainingIgnoreCase(String nome);

}
