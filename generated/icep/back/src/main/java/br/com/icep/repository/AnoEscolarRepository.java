package br.com.icep.repository;

import br.com.icep.entity.AnoEscolar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para AnoEscolar.
 */
@Repository
public interface AnoEscolarRepository extends JpaRepository<AnoEscolar, Integer>, JpaSpecificationExecutor<AnoEscolar> {

    /**
     * Busca por Nome (contém).
     */
    List<AnoEscolar> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca por Etapa (contém).
     */
    List<AnoEscolar> findByEtapaContainingIgnoreCase(String etapa);

}
