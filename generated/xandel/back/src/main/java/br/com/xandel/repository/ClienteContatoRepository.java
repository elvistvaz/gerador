package br.com.xandel.repository;

import br.com.xandel.entity.ClienteContato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para ClienteContato.
 */
@Repository
public interface ClienteContatoRepository extends JpaRepository<ClienteContato, Integer>, JpaSpecificationExecutor<ClienteContato> {

}
