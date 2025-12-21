package br.com.xandel.repository;

import br.com.xandel.entity.EstadoCivil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para EstadoCivil.
 */
@Repository
public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Integer>, JpaSpecificationExecutor<EstadoCivil> {

}
