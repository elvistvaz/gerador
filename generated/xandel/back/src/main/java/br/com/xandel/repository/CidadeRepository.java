package br.com.xandel.repository;

import br.com.xandel.entity.Cidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Cidade.
 */
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>, JpaSpecificationExecutor<Cidade> {

    /**
     * Busca por Nome da Cidade (contém).
     */
    List<Cidade> findByNomeCidadeContainingIgnoreCase(String nomeCidade);

    /**
     * Busca por UF (contém).
     */
    List<Cidade> findByUfContainingIgnoreCase(String uf);

}
