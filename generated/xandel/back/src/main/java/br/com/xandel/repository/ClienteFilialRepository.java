package br.com.xandel.repository;

import br.com.xandel.entity.ClienteFilial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para ClienteFilial.
 */
@Repository
public interface ClienteFilialRepository extends JpaRepository<ClienteFilial, Integer>, JpaSpecificationExecutor<ClienteFilial> {

    /**
     * Busca por Cliente.
     */
    List<ClienteFilial> findByIdCliente(Integer idCliente);

}
