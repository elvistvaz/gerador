package br.com.xandel.repository;

import br.com.xandel.entity.Cartorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Cartorio.
 */
@Repository
public interface CartorioRepository extends JpaRepository<Cartorio, Integer>, JpaSpecificationExecutor<Cartorio> {

    /**
     * Busca por Nome do Cartório (contém).
     */
    List<Cartorio> findByNomeCartorioContainingIgnoreCase(String nomeCartorio);

}
