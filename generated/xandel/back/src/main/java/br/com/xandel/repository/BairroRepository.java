package br.com.xandel.repository;

import br.com.xandel.entity.Bairro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Bairro.
 */
@Repository
public interface BairroRepository extends JpaRepository<Bairro, Integer>, JpaSpecificationExecutor<Bairro> {

    /**
     * Busca por Nome do Bairro (contém).
     */
    List<Bairro> findByNomeBairroContainingIgnoreCase(String nomeBairro);

}
