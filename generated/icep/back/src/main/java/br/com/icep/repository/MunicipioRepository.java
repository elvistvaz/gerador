package br.com.icep.repository;

import br.com.icep.entity.Municipio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Municipio.
 */
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer>, JpaSpecificationExecutor<Municipio> {

    /**
     * Busca por Nome (contém).
     */
    List<Municipio> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca por UF (contém).
     */
    List<Municipio> findByUfContainingIgnoreCase(String uf);

    /**
     * Busca por Território.
     */
    List<Municipio> findByTerritorioId(Integer territorioId);

}
