package br.com.xandel.repository;

import br.com.xandel.entity.Banco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para Banco.
 */
@Repository
public interface BancoRepository extends JpaRepository<Banco, String>, JpaSpecificationExecutor<Banco> {

    /**
     * Busca por Código (contém).
     */
    List<Banco> findByIdBancoContainingIgnoreCase(String idBanco);

    /**
     * Busca por Nome do Banco (contém).
     */
    List<Banco> findByNomeBancoContainingIgnoreCase(String nomeBanco);

}
