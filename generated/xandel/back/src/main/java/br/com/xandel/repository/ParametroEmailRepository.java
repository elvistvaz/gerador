package br.com.xandel.repository;

import br.com.xandel.entity.ParametroEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository para ParametroEmail.
 */
@Repository
public interface ParametroEmailRepository extends JpaRepository<ParametroEmail, Integer>, JpaSpecificationExecutor<ParametroEmail> {

}
